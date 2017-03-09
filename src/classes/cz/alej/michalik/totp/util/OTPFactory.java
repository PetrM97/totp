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
package cz.alej.michalik.totp.util;

/**
 * Factory pro vytvoření OTP třídy implementující fluent interface.
 * 
 * @author Petr Michalík
 * @see OTP
 */
public class OTPFactory {

	// Výchozí hodnoty
	int digits = 6;
	String alg = "HmacSHA1";
	int step = 30;
	long counter = 0;

	/**
	 * Vytvoří OTP třídu
	 * 
	 * @param otpType
	 *            OTP typ (TOTP nebo HOTP)
	 * @param secret
	 *            sdílené heslo
	 * @return vytvořená {@link OTP} třída
	 */

	public OTP getOTP(String otpType, byte[] secret) {
		if (otpType.equalsIgnoreCase("TOTP")) {
			return new TOTP().setSecret(secret).setDigits(digits).setAlgorithm(alg).setCounter(counter).setStep(step);
		} else if (otpType.equalsIgnoreCase("HOTP")) {
			return new HOTP().setSecret(secret).setDigits(digits).setAlgorithm(alg).setCounter(counter);
		}
		return null;
	}

	/**
	 * Nastaví algoritmus výpočtu HMAC hashe
	 * 
	 * @param algorithm
	 *            algoritmus
	 * @return sebe
	 */
	public OTPFactory setAlgorithm(String algorithm) {
		this.alg = algorithm;
		return this;
	}

	/**
	 * Nastaví počítadlo
	 * 
	 * @param counter
	 *            hodnota počítadla
	 * @return sebe
	 */
	public OTPFactory setCounter(long counter) {
		this.counter = counter;
		return this;
	}

	/**
	 * Nastaví počet číslic hesla v rozmezí od 6 do 8 číslic
	 * 
	 * @param d
	 *            počet číslic
	 * @return sebe
	 */
	public OTPFactory setDigits(int d) {
		this.digits = d;
		return this;
	}

	/**
	 * Nastaví jak často se budou hesla generovat. Lze použít pouze pro TOTP.
	 * 
	 * @param step
	 *            počet vteřin
	 * @return sebe
	 */
	public OTPFactory setStep(int step) {
		this.step = step;
		return this;
	}

}
