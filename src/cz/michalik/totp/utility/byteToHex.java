package cz.michalik.totp.utility;

/**
 * Třída pro zobrazení pole bytů jako hexadecimální hodnoty
 * 
 * @author Petr Michalík
 */
public class byteToHex {

	private byte[] bytes = null;
	private String[] res = null;

	/**
	 * Konstruktor pro převod na hexadecimální hodnoty
	 * 
	 * @param bytes
	 *            pole bytů k převodu
	 */
	public byteToHex(byte[] bytes) {
		this.bytes = bytes;
		convert();
	}

	/**
	 * Získat pole bytů jako pole hexadecimálních hodnot
	 * 
	 * @return pole s hexadecimálními hodnotami
	 */
	public String[] getArray() {
		return res.clone();

	}

	/**
	 * Získat pole bytů jako hexadecimální řetězec
	 * 
	 * @return řetězec s hexadecimálními hodnotami
	 */
	public String getString() {
		StringBuilder hex = new StringBuilder();
		for (String b : res) {
			hex.append(b);
			hex.append(" ");
		}
		return hex.toString().trim();
	}

	private void convert() {
		res = new String[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			res[i] = String.format("%02X", bytes[i]);
		}
	}

}
