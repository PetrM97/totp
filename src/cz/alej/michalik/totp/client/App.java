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

import javax.swing.*;

public class App {
	
	private final static String PATH = ".properties";
	public final static float FONT_SIZE = 64f;
	public static final Color COLOR = new Color(255,255,255);
	private static Properties p = new Properties();
	private static JFrame window = new JFrame("TOTP");
	
	public static void main(String[] args) {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(600, 150));
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
		window.setLocationByPlatform(true);
		
		loadProperties();
		p.setProperty("0", "Test;12345678901234567890");
		p.setProperty("1", "Heslo;Heslo");
		saveProperties();
		
		window.setVisible(true);
		
		for( Object data : p.values() ){
			window.add(new OtpPanel((String) data));
			System.out.println(data);
			window.add(new JSeparator());
		}
		
		window.add(new AddPanel());
		
		window.pack();
	}
	
	private static void loadProperties(){
		if( new File(PATH).exists() == false ){
			saveProperties();
		}
		try {
			p.load( new FileReader(PATH));
		} catch (FileNotFoundException e) {
			// Soubor nenalezen
			e.printStackTrace();
		} catch (IOException e) {
			// Chyba při načtení
			e.printStackTrace();
		}
	}
	
	private static void saveProperties(){
		try {
			p.store(new FileWriter(PATH), "TOTP save file");
		} catch (IOException e) {
			// Chyba při ukládání
			e.printStackTrace();
		}
	}

}
