package cz.michalik.totp.utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Třída pro generování HMAC hashe pomocí knihovny javax.crypto.*
 *
 * @author Petr Michalík
 * @see <a href="https://tools.ietf.org/html/rfc2104">RFC2104</a>
 */
public class HMAC {

	private byte[] message;
	private byte[] key;
	// Nastaven běžný algoritmus
	private String algorithm = "HmacSHA1";

	/**
	 * Vytvoří novou HMAC třídu a parametry jsou pole bytů
	 * 
	 * @param key
	 *            klíč
	 * @param msg
	 *            zpráva
	 */
	public HMAC(byte[] key, byte[] msg) {
		this.message = msg;
		this.key = key;
	}

	/**
	 * Nastaví algoritmus pro HMAC hash
	 * 
	 * @param alg
	 *            HMAC algoritmus
	 */
	public void setAlgorithm(String alg) {
		this.algorithm = alg;
	}
	
	/**
	 * Nastaví HMAC klíč
	 * 
	 * @param key klíč
	 */
	public void setKey(byte[] key) {
		this.key = key;
		
	}

	/**
	 * Nastaví HMAC zprávu
	 * 
	 * @param msg zpráva
	 */
	public void setMessage(byte[] msg) {
		this.message = msg;
		
	}

	/**
	 * Vrátí HMAC hash
	 * 
	 * @return pole bytů
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public byte[] get() {
		byte[] result = null;
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
			Mac mac = Mac.getInstance(algorithm);
			mac.init(keySpec);
			result = mac.doFinal(message);
		} catch (NoSuchAlgorithmException e) {
			// HmacSHA1 algoritmus nenalezen
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
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HMAC h = new HMAC("key".getBytes(), "The quick brown fox jumps over the lazy dog".getBytes());
		byte[] bytes = h.get();
		String hash = new byteToHex(bytes).getString();
		System.out.println(hash);
	}

}
