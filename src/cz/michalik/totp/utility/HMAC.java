package cz.michalik.totp.utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
/**
 * T��da pro generov�n� HMAC hashe pomoc� knihovny javax.crypto.*
 *
 * @author Petr Michal�k
 */
public class HMAC {
	
	private String message = null;
	private String key = null;
	// Nastaven b�n� algoritmus
	private String algorithm = "HmacSHA1";
	
	/**
     * Metoda vytvo�� novou pr�zdnou HMAC t��du 
	 * @return HMAC t��da
     */
	public HMAC HMAC(){
		return this;
	}
	
	/**
     * Nastav� zpr�vu pro HMAC hash
     * 
     * @param msg HMAC zpr�va
	 * @return HMAC t��da
     */
	public HMAC setMessage(String msg){
		this.message = msg;
		return this;
	}

	/**
     * Nastav� kl�� pro HMAC hash
     * 
     * @param key HMAC kl��
	 * @return HMAC t��da
     */
	public HMAC setKey(String key){
		this.key = key;
		return this;
	}
	
	/**
     * Nastav� algoritmus pro HMAC hash
     * 
     * @param alg HMAC algoritmus
	 * @return HMAC t��da
     */
	public HMAC setAlgorithm(String alg){
		this.algorithm = alg;
		return this;
	}

	/**
     * Vr�t� HMAC hash
     * 
     * @return pole byt�
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
			// �patn� kl��
			e.printStackTrace();
		}
		return result.clone();
	}
	
	/**
	 * Uk�zkov� metoda HMAC-SHA1 hashe
	 * pro zpr�vu "The quick brown fox jumps over the lazy dog"
	 * a kl�� "key"
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
