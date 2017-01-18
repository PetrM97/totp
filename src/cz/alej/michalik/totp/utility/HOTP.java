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

/**
 * Třída pro generování HOTP hesla z HMAC hashe podle RFC4226
 * 
 * @author Petr Michalík
 * @see HMAC
 * @see <a href="https://tools.ietf.org/html/rfc4226">RFC4226</a>
 * 
 */
public class HOTP {
	// Běžně se počítá heslo od nuly
	private int count = 0;
	// Počítadlo je 8-bytové pole
	private byte[] counter = new byte[8];
	// HMAC objekt
	private HMAC hmac = new HMAC();
	// Běžná délka hesla je 6 číslic
	private int digits = 6;

	/**
	 * Vytvoří prázdnou HOTP třídu
	 * 
	 * @param secret
	 *            heslo jako byte[]
	 */
	public HOTP() {
		setCounter(0);
	}
	
	/**
	 * Vytvoří novou HOTP třídu
	 * 
	 * @param secret
	 *            heslo jako byte[]
	 */
	public HOTP(byte[] secret) {
		hmac.setKey(secret);
		setCounter(0);
	}

	/**
	 * Vytvoří novou HOTP třídu pro danou hodnotu počítadla
	 * 
	 * @param secret
	 *            heslo jako byte[]
	 * @param count
	 *            hodnota počítadla
	 */
	public HOTP(byte[] secret, int count) {
		hmac.setKey(secret);
		setCounter(count);
	}

	/**
	 * Nastaví sdílené heslo
	 * 
	 * @param secret
	 *            heslo jako byte[]
	 */
	public void setSecret(byte[] secret) {
		hmac.setKey(secret);
	}

	/**
	 * Nastaví počítadlo
	 * 
	 * @param count
	 *            hodnota počítadla
	 */
	public void setCounter(int c) {
		count = c;
		// Je třeba z čísla udělat 8-bytové pole
		// viz http://stackoverflow.com/questions/9456913/is-this-rfc-4226-wrong
		for (int i = counter.length - 1; i >= 0; i--) {
			counter[i] = (byte) count;
			count >>= 8;
		}
		hmac.setMessage(counter);
	}

	/**
	 * Nastaví počet číslic hesla v rozmezí od 6 do 8 číslic
	 * 
	 * @param digits
	 *            počet číslic
	 * @throws Error
	 *             špatný počet číslic
	 */
	public void setDigits(int digits) {
		if (digits >= 6 && digits <= 8) {
			this.digits = digits;
		} else {
			throw new Error("Minimální délka je 6 čislic a maximální délka je 8 číslic");
		}
	}

	/**
	 * Nastaví algoritmus výpočtu HMAC hashe
	 * 
	 * @param alg
	 *            algoritmus
	 */
	public void setAlgorithm(String alg) {
		hmac.setAlgorithm(alg);
	}

	/**
	 * Vypočítá HOTP heslo pro danou hodnotu počítadla
	 * 
	 * @return HOTP heslo jako int
	 */
	public int get() {
		byte[] hmac_result = hmac.get();
		// Offset je hodnota posledních 4 bitů z posledního bytu
		// K rozdeleni pouziju bitovou operaci
		int offset = hmac_result[hmac_result.length - 1] & 0xf;
		// Heslo je vytvoreno ze 4 nasledujicich bytu od offsetu
		// 0x7f = 01111111 = 127
		// 0xff = 11111111 = 255
		// Zaroven odstranim hodnotu 1. bitu, ktere urcuje znamenko
		// bin_code je tedy kladne binarni big-endian cislo tvorene 31 bity
		int bin_code = (hmac_result[offset] & 0x7f) << 24 | (hmac_result[offset + 1] & 0xff) << 16
				| (hmac_result[offset + 2] & 0xff) << 8 | (hmac_result[offset + 3] & 0xff);
		// HOTP je hodnota po deleni 10^digits - ziskame dany pocet mist
		Double hotp = bin_code % Math.pow(10, digits);
		// Z doublu ziskame celociselny integer
		int pass = hotp.intValue();
		// Zvýší hodnotu počítadla - po každém zavolání funkce vrací jinou
		// hodnotu
		count++;
		setCounter(count);
		return pass;
	}

	/**
	 * Vypočítá HOTP heslo a vrátí řetězec znaků
	 * 
	 * @return HOTP heslo jako řetězec znaků
	 */
	public String toString() {
		int hotp = get();
		// Číslo doplní zleva nulami
		return String.format("%0" + digits + "d", hotp);
	}

	public static void main(String[] args) {
		HOTP h = new HOTP("12345678901234567890".getBytes());
		System.out.printf("HOTP heslo je: %s\n", h);
	}
}
