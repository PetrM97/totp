package cz.michalik.totp.utility;

import java.time.Instant;

/**
 * T��da pro generov�n� TOTP hesla pomoc� HOTP algoritmu podle RFC6238 specifikace
 * 
 * @author Petr Michal�k
 * @see HOTP
 * @see <a href="https://tools.ietf.org/html/rfc6238">RFC6238</a>
 * 
 */
public class TOTP {
	// B�n� se hesla generuj� ka�d�ch 30 sekund
	private int step = 30;
	// Trida pro generovani hesla
	private HOTP hotp;
	/**
	 * Vytvo�� novou TOTP t��du
	 * @param secret string kl��e
	 */
	public TOTP(String secret){
		hotp = new HOTP(secret);
	}
	/**
	 * Nastav� po�et ��slic hesla v rozmez� od 6 do 8 ��slic
	 * 
	 * @param digits
	 *            po�et ��slic
	 * @throws Error
	 *             �patn� po�et ��slic
	 */
	public void setDigits(int digits){
		hotp.setDigits(digits);
	}
	/**
	 * Nastav� jak �asto se budou hesla generovat 
	 * @param step po�et vte�in
	 */
	public void setStep(int step){
		this.step = step;
	}
	/**
	 * Vr�t� Unix time = po�et vte�in od 1.1.1970
	 * @return int
	 */
	public int getTime(){
		long timestamp = Instant.now().toEpochMilli()/1000;
		if ( timestamp < (long)Integer.MAX_VALUE ) {
			return (int) timestamp;
		}else{
			throw new Error("�asov� hodnota je v�t�� ne� maxim�ln� hodnota integeru");
		}
	}
	/**
	 * Vr�t� TOTP heslo pro aktu�ln� �as
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
