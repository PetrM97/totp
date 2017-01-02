package cz.michalik.totp.utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
/**
 * Tøída pro generování HMAC hashe pomocí knihovny javax.crypto.*
 *
 * @author Petr Michalík
 */
public class HMAC {
	
	private String message = null;
	private String key = null;
	// Nastaven bìžný algoritmus
	private String algorithm = "HmacSHA1";
	
	/**
     * Metoda vytvoøí novou prázdnou HMAC tøídu 
	 * @return HMAC tøída
     */
	public HMAC HMAC(){
		return this;
	}
	
	/**
     * Nastaví zprávu pro HMAC hash
     * 
     * @param msg HMAC zpráva
	 * @return HMAC tøída
     */
	public HMAC setMessage(String msg){
		this.message = msg;
		return this;
	}

	/**
     * Nastaví klíè pro HMAC hash
     * 
     * @param key HMAC klíè
	 * @return HMAC tøída
     */
	public HMAC setKey(String key){
		this.key = key;
		return this;
	}
	
	/**
     * Nastaví algoritmus pro HMAC hash
     * 
     * @param alg HMAC algoritmus
	 * @return HMAC tøída
     */
	public HMAC setAlgorithm(String alg){
		this.algorithm = alg;
		return this;
	}

	/**
     * Vrátí HMAC hash
     * 
     * @return pole bytù
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
	public byte[] get() {
		byte[] result = null;		
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
			Mac mac = Mac.getInstance(algorithm);
			mac.init(keySpec);
			result = mac.doFinal(message.getBytes());			
		} catch (NoSuchAlgorithmException e) {
			// HmacSHA1 algoritmus nenalezen
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// Špatný klíè
			e.printStackTrace();
		}
		return result.clone();
	}
	
	/**
	 * Ukázková metoda HMAC-SHA1 hashe
	 * pro zprávu "The quick brown fox jumps over the lazy dog"
	 * a klíè "key"
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] bytes = new HMAC().setMessage("The quick brown fox jumps over the lazy dog")
								.setKey("key")
								.get();
		String hash = new byteToHex(bytes).getString();
		System.out.println(hash);		
	}

}
