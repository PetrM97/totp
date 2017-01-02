package cz.michalik.totp.utility;

/**
 * Tøída pro generování HOTP hesla z HMAC hashe podle RFC4226
 * 
 * @author Petr Michalík
 * @see HMAC
 * @see <a href="https://tools.ietf.org/html/rfc4226">RFC4226</a>
 * 
 */
public class HOTP {
	private int count;
	private byte[] counter;
	// Tajne heslo sdílené mezi klientem a serverem
	private String secret = null;
	// Bìžná délka hesla je 6 èíslic
	private int digits = 6;

	/**
	 * Vytvoøí novou HOTP tøídu
	 * 
	 * @param secret
	 *            string klíèe
	 */
	public HOTP(String secret) {
		this.secret = secret;
		setCounter(0);
	}

	/**
	 * Nastaví poèítadlo
	 * 
	 * @param count
	 *            hodnota poèítadla
	 */
	public void setCounter(int count) {
		this.count = count;
	}

	/**
	 * Nastaví poèet èíslic hesla v rozmezí od 6 do 8 èíslic
	 * 
	 * @param digits
	 *            poèet èíslic
	 * @throws Error
	 *             špatný poèet èíslic
	 */
	public void setDigits(int digits) {
		if (digits >= 6 && digits <= 8) {
			this.digits = digits;
		} else {
			throw new Error("Minimální délka je 6 èislic a maximální délka je 8 èíslic");
		}
	}

	/**
	 * Vypoèítá HOTP heslo pro danou hodnotu poèítadla
	 * 
	 * @return HOTP heslo jako int
	 */
	public int get() {
		counter = new byte[8];
		// Je tøeba z èísla udìlat pole bytù
		// viz http://stackoverflow.com/questions/9456913/is-this-rfc-4226-wrong
		for (int i = counter.length - 1; i >= 0; i--) {
			counter[i] = (byte) count;
			count >>= 8;
		}
		HMAC hmac = new HMAC(secret.getBytes(), counter);
		byte[] hmac_result = hmac.get();
		// Offset je hodnota posledních 4 bitù z posledního bytu
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
		// Zvýší hodnotu poèítadla - po každém zavolání funkce vrací jinou
		// hodnotu
		count++;
		return pass;
	}

	/**
	 * Vypoèítá HOTP heslo a vrátí øetìzec znakù
	 * 
	 * @return HOTP heslo jako øetìzec znakù
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
