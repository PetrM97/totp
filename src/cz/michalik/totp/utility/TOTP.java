package cz.michalik.totp.utility;

import java.time.Instant;

/**
 * Tøída pro generování TOTP hesla pomocí HOTP algoritmu podle RFC6238 specifikace
 * 
 * @author Petr Michalík
 * @see HOTP
 * @see <a href="https://tools.ietf.org/html/rfc6238">RFC6238</a>
 * 
 */
public class TOTP {
	// Bìžnì se hesla generují každých 30 sekund
	private int step = 30;
	// Trida pro generovani hesla
	private HOTP hotp;
	/**
	 * Vytvoøí novou TOTP tøídu
	 * @param secret string klíèe
	 */
	public TOTP(String secret){
		hotp = new HOTP(secret);
	}
	/**
	 * Nastaví poèet èíslic hesla v rozmezí od 6 do 8 èíslic
	 * 
	 * @param digits
	 *            poèet èíslic
	 * @throws Error
	 *             špatný poèet èíslic
	 */
	public void setDigits(int digits){
		hotp.setDigits(digits);
	}
	/**
	 * Nastaví jak èasto se budou hesla generovat 
	 * @param step poèet vteøin
	 */
	public void setStep(int step){
		this.step = step;
	}
	/**
	 * Vrátí Unix time = poèet vteøin od 1.1.1970
	 * @return int
	 */
	public int getTime(){
		long timestamp = Instant.now().toEpochMilli()/1000;
		if ( timestamp < (long)Integer.MAX_VALUE ) {
			return (int) timestamp;
		}else{
			throw new Error("Èasová hodnota je vìtší než maximální hodnota integeru");
		}
	}
	/**
	 * Vrátí TOTP heslo pro aktuální èas
	 * @return TOTP heslo jako int
	 */
	public int get(){
		hotp.setCounter(getTime()/step);
		return hotp.get();
	}
	public static void main(String[] args) {
		TOTP t = new TOTP("12345678901234567890");
		int p = t.get();
		System.out.println(p);
	}

}
