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
		return p;
	}

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

	public static int add(String data) {
		int index = 0;
		while (p.containsKey(String.valueOf(index))) {
			index++;
		}
		p.put(String.valueOf(index), data);
		save();
		return index;
	}
}
