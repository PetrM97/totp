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
package cz.alej.michalik.totp.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Data {

	static Properties p = new Properties();
	final static String PATH = "server.properties";

	/**
	 * Načte nastavení ze souboru
	 * 
	 * @return Properties
	 */
	public static Properties load() {
		// Vytvoří soubor při prvním spuštění
		if (new File(PATH).exists() == false) {
			save();
		}
		try {
			p.load(new FileReader(PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Demo ukázka
		p.put("demo", "GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ");
		return p;
	}

	/**
	 * Uloží nastavení do souboru
	 */
	public static void save() {
		try {
			p.store(new FileWriter(PATH), "TOTP server data");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Přidá uživatele
	 * 
	 * @param key
	 *            unikátní uživatelské jméno
	 * @param data
	 *            TOTP sdílené heslo
	 * @param rewrite
	 *            zda má přepsat data existujícího uživatele
	 * @return jestli se data zapsala
	 */
	public static boolean add(String key, String data, boolean rewrite) {
		load();
		if (p.containsKey(key) && !rewrite) {
			// Nepřepíše současný záznam
			return false;
		}
		p.put(key, data);
		save();
		return true;
	}

	/**
	 * Získá data uživatele
	 * 
	 * @param user
	 *            uživatelské jméno
	 * @return sdílené heslo
	 */
	public static String get(String user) {
		load();
		return p.getProperty(user);
	}

	/**
	 * Smaže uživatele
	 * 
	 * @param user
	 *            uživatelské jméno
	 */
	public static void remove(String user) {
		p.remove(user);
		save();
	}

	/**
	 * Smaže vše
	 */
	public static void deleteAll() {
		p = new Properties();
		save();
	}

	/**
	 * Zjistí jestli uživatel existuje
	 * 
	 * @param user
	 *            uživatelské jméno
	 * @return boolean
	 */
	public static boolean exists(String user) {
		load();
		if (p.containsKey(user)) {
			return true;
		}
		return false;
	}
}
