package cz.michalik.totp.utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * T��da pro generov�n� HMAC hashe pomoc� knihovny javax.crypto.*
 *
 * @author Petr Michal�k
 * @see <a href="https://tools.ietf.org/html/rfc2104">RFC2104</a>
 */
public class HMAC {

	private byte[] message;
	private byte[] key;
	// Nastaven b�n� algoritmus
	private String algorithm = "HmacSHA1";

	/**
	 * Vytvo�� novou HMAC t��du a parametry jsou pole byt�
	 * 
	 * @param key
	 *            kl��
	 * @param msg
	 *            zpr�va
	 */
	public HMAC(byte[] key, byte[] msg) {
		this.message = msg;
		this.key = key;
	}

	/**
	 * Nastav� algoritmus pro HMAC hash
	 * 
	 * @param alg
	 *            HMAC algoritmus
	 */
	public void setAlgorithm(String alg) {
		this.algorithm = alg;
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
			SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
			Mac mac = Mac.getInstance(algorithm);
			mac.init(keySpec);
			result = mac.doFinal(message);
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
	 * Uk�zkov� metoda HMAC-SHA1 hashe pro zpr�vu "The quick brown fox jumps
	 * over the lazy dog" a kl�� "key"
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
