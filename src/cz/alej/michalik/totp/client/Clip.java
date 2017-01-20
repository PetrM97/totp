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

	public static void main(String[] args) {
		Clip a = new Clip();
		a.set("Ahoj");
		System.out.println(a.get());
	}

}
