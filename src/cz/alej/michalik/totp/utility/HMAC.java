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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Fluent interface pro generování HMAC hashe pomocí knihovny javax.crypto.*
 *
 * @author Petr Michalík
 * @see <a href="https://tools.ietf.org/html/rfc2104">RFC2104</a>
 */
public class HMAC {

	private byte[] message = " ".getBytes();
	private byte[] key = " ".getBytes();
	// Nastaven běžný algoritmus
	private String algorithm = "HmacSHA1";

	/**
	 * Vytvoří novou HMAC třídu
	 * 
	 * @return vytořená HMAC třída
	 */
	public HMAC HMAC() {
		return this;
	}

	/**
	 * Nastaví algoritmus pro HMAC hash
	 * 
	 * @param alg
	 *            HMAC algoritmus
	 * @return
	 */
	public HMAC setAlgorithm(String alg) {
		this.algorithm = alg;
		return this;
	}

	/**
	 * Nastaví HMAC klíč
	 * 
	 * @param key
	 *            klíč
	 * @return
	 */
	public HMAC setKey(byte[] key) {
		this.key = key;
		return this;

	}

	/**
	 * Nastaví HMAC zprávu
	 * 
	 * @param msg
	 *            zpráva
	 * @return
	 */
	public HMAC setMessage(byte[] msg) {
		this.message = msg;
		return this;

	}

	/**
	 * Vrátí HMAC hash
	 * 
	 * @return pole bytů
	 * @throws InvalidKeyException
	 *             špatný klíč
	 * @throws NoSuchAlgorithmException
	 *             neexistující algoritmus
	 */
	public byte[] get() {
		byte[] result = null;
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
			Mac mac = Mac.getInstance(algorithm);
			mac.init(keySpec);
			result = mac.doFinal(message);
		} catch (NoSuchAlgorithmException e) {
			// algoritmus nenalezen
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// Špatný klíč
			e.printStackTrace();
		}
		return result.clone();
	}

	/**
	 * Ukázková metoda HMAC-SHA1 hashe pro zprávu "The quick brown fox jumps
	 * over the lazy dog" a klíč "key"
	 */
	public static void main(String[] args) {
		HMAC h = new HMAC().setKey("key".getBytes())
				.setMessage("The quick brown fox jumps over the lazy dog".getBytes());
		byte[] bytes = h.get();
		System.out.println(new byteToHex(bytes));
	}

}
