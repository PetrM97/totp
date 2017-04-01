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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * Grafické prostředí pro generování TOTP kódů
 * 
 * @author Petr Michalík
 *
 */
public class App {
	private final static String FILENAME = "client.properties";
	public final static float FONT_SIZE = 48f;
	public static final Color COLOR = new Color(255, 255, 255);
	// Záznamy
	private static Properties p = new Properties();
	// Okno
	private static JFrame window = new JFrame("TOTP");
	// Panel pro posun v hlavním panelu se záznamy
	private static JScrollPane scrollFrame = new JScrollPane();
	// Tlačítko pro přidání nových záznamů
	private static JPanel ap = new AddPanel(p);

	/**
	 * Spustí grafické prostředí
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/* Parametry okna */
		// Ukončení programu při zavření
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(320, 240));
		// Rozvržení obsahu podle osy Y
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
		// Umístění okna určí OS
		window.setLocationByPlatform(true);
		// Nastavím ikonu
		try {
			String path = "/material-design-icons/action/drawable-xhdpi/ic_lock_black_48dp.png";
			window.setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource(path)));
		} catch (NullPointerException e) {
			System.out.println("Icon not found");
		}

		p.setProperty("-1", "Test;GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ");

		// Načte uložené hodnoty
		loadProperties();
		saveProperties();

		// Vykreslí okno
		window.setVisible(true);
		window.pack();
	}

	/**
	 * Načte nastavení a vykreslí změny do aplikace
	 */
	public static void loadProperties() {
		System.out.println("Překresluji");
		// Vytvoří soubor při prvním spuštění
		try {
			System.out.println("Read: " + App.class.getResource("/" + FILENAME).toString());
		} catch (NullPointerException e) {
			saveProperties();
		}
		// Načte nastavení ze souboru
		try {
			p.load(App.class.getResourceAsStream("/" + FILENAME));
		} catch (FileNotFoundException e) {
			// Soubor nenalezen
			e.printStackTrace();
		} catch (IOException e) {
			// Chyba při načtení
			e.printStackTrace();
		}

		// Odstraním současný obsah
		window.remove(scrollFrame);
		window.remove(ap);
		// Aktualizuji obsah
		ap = new AddPanel(p);
		scrollFrame = new JScrollPane(new MainPanel(p));

		// Nastavení hlavního panelu se záznamy
		scrollFrame.setPreferredSize(new Dimension(640, 480));
		// Nastavení pro plynulé scrollování
		scrollFrame.getVerticalScrollBar().setUnitIncrement(16);
		scrollFrame.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

		// Do okna přidám panely s novým obsahem
		window.add(scrollFrame);
		window.add(ap);

		window.repaint();
		window.setVisible(true);
	}

	/**
	 * Uloží nastavení do souboru
	 */
	public static void saveProperties() {
		System.out.println("Trying to save file");
		try {
			// Získám relativní cestu - složka ve které běží spuštěná aplikace
			String dir = App.class.getResource("/").getFile();
			OutputStream os = new FileOutputStream(dir + FILENAME);
			// Ulozim
			p.store(os, "TOTP save file");
			System.out.println("Created file in " + dir);
		} catch (IOException e) {
			// Chyba při ukládání
			e.printStackTrace();
		}
	}

}
