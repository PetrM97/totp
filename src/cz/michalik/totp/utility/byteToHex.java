package cz.michalik.totp.utility;
/**
 * T��da pro zobrazen� pole byt� jako hexadecim�ln� hodnoty
 * 
 * @author Petr Michal�k
 */
public class byteToHex {
	
	private byte[] bytes = null;
	private String[] res = null;

	/**
	 * Konstruktor pro p�evod na hexadecim�ln� hodnoty
	 * 
	 * @param bytes Byte array to convert
	 */
	public byteToHex(byte[] bytes) {
		this.bytes = bytes;
		convert();
	}
	
	/**
	 * Z�skat pole byt� jako pole hexadecim�ln�ch hodnot
	 * 
	 * @return Pole s hexadecim�ln�mi hodnotami
	 */
	public String[] getArray(){
		return res.clone();
		
	}
	
	/**
	 * Z�skat pole byt� jako hexadecim�ln� �et�zec
	 * 
	 * @return �et�zec s hexadecim�ln�mi hodnotami
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
