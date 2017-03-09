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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	private final static String PATH = "client.properties";
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
		if (new File(PATH).exists() == false) {
			saveProperties();
		}
		// Načte nastavení ze souboru
		try {
			p.load(new FileReader(PATH));
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
		try {
			p.store(new FileWriter(PATH), "TOTP save file");
		} catch (IOException e) {
			// Chyba při ukládání
			e.printStackTrace();
		}
	}

}
