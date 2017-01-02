package cz.michalik.totp.utility;

/**
 * T��da pro generov�n� HOTP hesla z HMAC hashe podle RFC4226
 * 
 * @author Petr Michal�k
 * @see HMAC
 * @see <a href="https://tools.ietf.org/html/rfc4226">RFC4226</a>
 * 
 */
public class HOTP {
	private int count;
	private byte[] counter;
	// Tajne heslo sd�len� mezi klientem a serverem
	private String secret = null;
	// B�n� d�lka hesla je 6 ��slic
	private int digits = 6;

	/**
	 * Vytvo�� novou HOTP t��du
	 * 
	 * @param secret
	 *            string kl��e
	 */
	public HOTP(String secret) {
		this.secret = secret;
		setCounter(0);
	}

	/**
	 * Nastav� po��tadlo
	 * 
	 * @param count
	 *            hodnota po��tadla
	 */
	public void setCounter(int count) {
		this.count = count;
	}

	/**
	 * Nastav� po�et ��slic hesla v rozmez� od 6 do 8 ��slic
	 * 
	 * @param digits
	 *            po�et ��slic
	 * @throws Error
	 *             �patn� po�et ��slic
	 */
	public void setDigits(int digits) {
		if (digits >= 6 && digits <= 8) {
			this.digits = digits;
		} else {
			throw new Error("Minim�ln� d�lka je 6 �islic a maxim�ln� d�lka je 8 ��slic");
		}
	}

	/**
	 * Vypo��t� HOTP heslo pro danou hodnotu po��tadla
	 * 
	 * @return HOTP heslo jako int
	 */
	public int get() {
		counter = new byte[8];
		// Je t�eba z ��sla ud�lat pole byt�
		// viz http://stackoverflow.com/questions/9456913/is-this-rfc-4226-wrong
		for (int i = counter.length - 1; i >= 0; i--) {
			counter[i] = (byte) count;
			count >>= 8;
		}
		HMAC hmac = new HMAC(secret.getBytes(), counter);
		byte[] hmac_result = hmac.get();
		// Offset je hodnota posledn�ch 4 bit� z posledn�ho bytu
		// K rozdeleni pouziju bitovou operaci
		int offset = hmac_result[hmac_result.length - 1] & 0xf;
		// Heslo je vytvoreno ze 4 nasledujicich bytu od offsetu
		// 0x7f = 01111111 = 127
		// 0xff = 11111111 = 255
		// Zaroven odstranim hodnotu 1. bitu, ktere urcuje znamenko
		// bin_code je tedy kladne binarni big-endian cislo tvorene 31 bity
		int bin_code = (hmac_result[offset] & 0x7f) << 24 
				| (hmac_result[offset + 1] & 0xff) << 16
				| (hmac_result[offset + 2] & 0xff) << 8 
				| (hmac_result[offset + 3] & 0xff);
		// HOTP je hodnota po deleni 10^digits - ziskame dany pocet mist
		Double hotp = bin_code % Math.pow(10, digits);
		// Z doublu ziskame celociselny integer
		int pass = hotp.intValue();
		// Zv��� hodnotu po��tadla - po ka�d�m zavol�n� funkce vrac� jinou
		// hodnotu
		count++;
		return pass;
	}

	/**
	 * Vypo��t� HOTP heslo a vr�t� �et�zec znak�
	 * 
	 * @return HOTP heslo jako �et�zec znak�
	 */
	public String getString() {
		int hotp = get();
		return String.format("%6d", hotp);
	}

	public static void main(String[] args) {
		HOTP h = new HOTP("12345678901234567890");
		int pass = h.get();
		System.out.printf("HOTP heslo je: %6d", pass);
	}
}
