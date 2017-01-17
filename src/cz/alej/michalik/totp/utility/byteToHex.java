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
