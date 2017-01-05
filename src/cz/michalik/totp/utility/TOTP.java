package cz.michalik.totp.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Třída pro generování TOTP hesla pomocí HOTP algoritmu podle RFC6238 specifikace
 * 
 * @author Petr Michalík
 * @see HOTP
 * @see <a href="https://tools.ietf.org/html/rfc6238">RFC6238</a>
 * 
 */
public class TOTP {
	// Běžně se hesla generují každých 30 sekund
	private int step = 30;
	// Třída pro generování hesla
	private HOTP hotp;
	/**
	 * Vytvoří novou TOTP třídu
	 * @param secret string klíče
	 */
	public TOTP(String secret){
		hotp = new HOTP(secret.getBytes());
	}
	/**
	 * Nastaví počet číslic hesla v rozmezí od 6 do 8 číslic
	 * 
	 * @param digits
	 *            počet číslic
	 * @throws Error
	 *             špatný počet číslic
	 */
	public void setDigits(int digits){
		hotp.setDigits(digits);
	}
	/**
	 * Nastaví jak často se budou hesla generovat 
	 * @param step počet vteřin
	 */
	public void setStep(int step){
		this.step = step;
	}
	/**
	 * Vrátí Unix time = počet vteřin od 1.1.1970
	 * @return int
	 */
	public int getTime(){
		long timestamp = new Date().getTime() / 1000;
		if ( timestamp < (long)Integer.MAX_VALUE ) {
			return (int) timestamp;
		}else{
			throw new Error("Časová hodnota je větší než maximální hodnota integeru");
		}
	}
	/**
	 * Vrátí TOTP heslo pro aktuální čas
	 * @return TOTP heslo jako int
	 */
	public int get(){
		hotp.setCounter(getTime()/step);
		return hotp.get();
	}
	/**
	 * Ukázka TOTP pro zprávu "12345678901234567890"
	 * @param args
	 */
	public static void main(String[] args) {
		// GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ v base32
		String secret = "12345678901234567890";
		TOTP t = new TOTP(secret);
		System.out.printf("%d\n",t.get());
		while(true){
			int time = Integer.valueOf(new SimpleDateFormat("ss").format(new Date()));
			if(time % 30 == 0){
				System.out.printf("%d\n",t.get());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
