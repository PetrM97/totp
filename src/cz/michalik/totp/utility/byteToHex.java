package cz.michalik.totp.utility;
/**
 * Tøída pro zobrazení pole bytù jako hexadecimální hodnoty
 * 
 * @author Petr Michalík
 */
public class byteToHex {
	
	private byte[] bytes = null;
	private String[] res = null;

	/**
	 * Konstruktor pro pøevod na hexadecimální hodnoty
	 * 
	 * @param bytes Byte array to convert
	 */
	public byteToHex(byte[] bytes) {
		this.bytes = bytes;
		convert();
	}
	
	/**
	 * Získat pole bytù jako pole hexadecimálních hodnot
	 * 
	 * @return Pole s hexadecimálními hodnotami
	 */
	public String[] getArray(){
		return res.clone();
		
	}
	
	/**
	 * Získat pole bytù jako hexadecimální øetìzec
	 * 
	 * @return Øetìzec s hexadecimálními hodnotami
	 */
	public String getString(){
		StringBuilder hex = new StringBuilder();
		for( String b : res ){
			hex.append(b);
		}
		return hex.toString();
	}
	
	private void convert(){
		res = new String[bytes.length];
		for( int i = 0; i < bytes.length; i++ ){
			res[i] = String.format("%02X", bytes[i]);
		}
	}

}
