/*
   Copyright 2017 Petr Michalík

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package cz.alej.michalik.totp.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base32;

/**
 * Třída pro generování TOTP hesla pomocí HOTP algoritmu podle RFC6238
 * specifikace
 * 
 * @author Petr Michalík
 * @see HOTP
 * @see <a href="https://tools.ietf.org/html/rfc6238">RFC6238</a>
 * 
 */
public class TOTP implements OTP {
	// Běžně se hesla generují každých 30 sekund
	private int step = 30;
	// Časový posun
	private long t0 = 0;
	// Třída pro generování hesla
	private HOTP hotp = new HOTP();

	/**
	 * Vytvoří prázdnou TOTP třídu
	 */
	public TOTP() {

	}

	/**
	 * Vytvoří novou TOTP třídu
	 * 
	 * @param secret
	 *            string klíče
	 */
	public TOTP(byte[] secret) {
		hotp.setSecret(secret);
	}

	/**
	 * Nastaví sdílené heslo
	 * 
	 * @param secret
	 *            heslo jako byte[]
	 * @return TOTP třída
	 */
	public TOTP setSecret(byte[] secret) {
		hotp.setSecret(secret);
		return this;
	}

	/**
	 * Nastaví počet číslic hesla v rozmezí od 6 do 8 číslic
	 * 
	 * @param digits
	 *            počet číslic
	 * @return TOTP třída
	 * @throws Error
	 *             špatný počet číslic
	 */
	public TOTP setDigits(int digits) {
		hotp.setDigits(digits);
		return this;
	}

	/**
	 * Nastaví jak často se budou hesla generovat
	 * 
	 * @param step
	 *            počet vteřin
	 * @return TOTP třída
	 */
	public TOTP setStep(int step) {
		this.step = step;
		return this;
	}

	/**
	 * Nastaví algoritmus výpočtu HMAC hashe
	 * 
	 * @param alg
	 *            algoritmus
	 * @return TOTP třída
	 */
	public TOTP setAlgorithm(String alg) {
		hotp.setAlgorithm(alg);
		return this;
	}

	/**
	 * Nastaví časový posun
	 * 
	 * @param time
	 *            čas v sekundách
	 * @return TOTP třída
	 */
	public TOTP setShift(long time) {
		t0 = time;
		return this;
	}

	/**
	 * Vrátí nastavený počet číslic
	 * 
	 * @return počet číslic
	 */
	public int getDigits() {
		return hotp.getDigits();
	}

	/**
	 * Vrátí nastavenou periodu generování hesla
	 * 
	 * @return čas v sekundách
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Vrátí nastavený algoritmus
	 * 
	 * @return algoritmus
	 */
	public String getAlgorithm() {
		return hotp.getAlgorithm();
	}

	/**
	 * Vrátí nastavený časový posun
	 * 
	 * @return čas v sekundách
	 */
	public long getShift() {
		return t0;
	}

	/**
	 * Vrátí TOTP heslo pro aktuální čas
	 * 
	 * @return TOTP heslo jako int
	 */
	public int get() {
		long now = System.currentTimeMillis() / 1000;
		hotp.setCounter((int) Math.floor((now - t0) / step));
		return hotp.get();
	}

	/**
	 * Vrátí TOTP heslo pro aktuální čas
	 * 
	 * @return řetězec hesla
	 */
	public String toString() {
		int pass = get();
		// Číslo doplní zleva nulami
		return String.format("%0" + hotp.getDigits() + "d", pass);
	}

	/**
	 * Ukázka TOTP pro zprávu "12345678901234567890"
	 */
	public static void main(String[] args) {
		// GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ v base32
		String secret = "12345678901234567890";
		System.out.printf("Heslo je: %s \nV Base32 to je: %s\n", secret,
				new Base32().encodeToString(secret.getBytes()));
		TOTP t = new TOTP(secret.getBytes());
		System.out.printf("%s\n", t);
		while (true) {
			int time = Integer.valueOf(new SimpleDateFormat("ss").format(new Date()));
			if (time % 30 == 0) {
				System.out.printf("%s\n", t);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
