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
package cz.alej.michalik.totp.client;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Třída pro práci se systémovou schránkou
 * 
 * @author Petr Michalík
 *
 */
public class Clip {

	// Vytvoří objekt schránky
	Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

	/**
	 * Vytvoří prázdnou třídu
	 */
	public Clip() {
	}

	/**
	 * Vloží text do schránky
	 * 
	 * @param text
	 *            textový řetězec
	 */
	public void set(String text) {
		StringSelection string = new StringSelection(text);
		clip.setContents(string, null);
	}

	/**
	 * Vrátí obsah schránky
	 * 
	 * @return textový řetězec
	 */
	public String get() {
		String result = "";
		Transferable contents = clip.getContents(null);
		try {
			result = (String) contents.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			// Ve schránce je jiný datový typ
			// Vyjímku ignoruji a vracím prázdný řetězec
		} catch (IOException e) {
			// Data ve schránce se změnila
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Ukázka
	 * @param args
	 */
	public static void main(String[] args) {
		Clip a = new Clip();
		a.set("Ahoj");
		System.out.println(a.get());
	}

}
