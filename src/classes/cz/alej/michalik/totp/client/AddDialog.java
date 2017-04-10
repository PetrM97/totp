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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.codec.binary.Base32;

/**
 * Okno pro přidání nového záznamu
 * 
 * @author Petr Michalík
 *
 */
@SuppressWarnings("serial")
public class AddDialog extends JFrame {
	public AddDialog(final Properties prop) {
		System.out.println("Pridat novy zaznam");
		this.setTitle("Přidat");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 150));
		this.setLocationByPlatform(true);
		// Panel pro vytvoření okrajů
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridBagLayout());
		// Vlastnosti pro popisky
		GridBagConstraints label = new GridBagConstraints();
		label.insets = new Insets(2, 2, 2, 2);
		label.fill = GridBagConstraints.NONE;
		label.weightx = 1;
		// Vlastnosti pro pole
		GridBagConstraints field = new GridBagConstraints();
		field.insets = new Insets(2, 2, 2, 2);
		field.fill = GridBagConstraints.HORIZONTAL;
		field.weightx = 10;

		this.add(panel);

		// Nastavím ikonu okna
		try {
			String path = "/material-design-icons/content/drawable-xhdpi/ic_create_black_48dp.png";
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource(path)));
		} catch (NullPointerException ex) {
			System.out.println("Icon not found");
		}

		// Pole pro pojmenování záznamu
		// Ikona
		ImageIcon icon = null;
		try {
			String path = "/material-design-icons/editor/drawable-xhdpi/ic_format_color_text_black_18dp.png";
			icon = new ImageIcon(App.class.getResource(path));
		} catch (NullPointerException ex) {
			System.out.println("Icon not found");
		}
		// Přidání labelu s ikonou a nastavím velikost písma
		label.gridy = 0;
		field.gridy = 0;
		JLabel nameLabel = new JLabel("Název: ", icon, JLabel.CENTER);
		nameLabel.setFont(nameLabel.getFont().deriveFont(App.FONT_SIZE * 2 / 3));
		panel.add(nameLabel, label);
		// Pole pro jméno
		final JTextField name = new JTextField();
		name.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		name.setFont(name.getFont().deriveFont(App.FONT_SIZE * 2 / 3));
		panel.add(name, field);

		// Pole pro zadání sdíleného hesla
		// Ikona
		icon = null;
		try {
			String path = "/material-design-icons/hardware/drawable-xhdpi/ic_security_black_18dp.png";
			icon = new ImageIcon(App.class.getResource(path));
		} catch (NullPointerException ex) {
			System.out.println("Icon not found");
		}
		// Přidání labelu s ikonou
		label.gridy = 1;
		field.gridy = 1;
		JLabel secretLabel = new JLabel("Heslo: ", icon, JLabel.CENTER);
		secretLabel.setFont(secretLabel.getFont().deriveFont(App.FONT_SIZE * 2 / 3));
		panel.add(secretLabel, label);
		// Pole pro heslo
		final JTextField secret = new JTextField();
		secret.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		secret.setFont(secret.getFont().deriveFont(App.FONT_SIZE * 2 / 3));
		panel.add(secret, field);

		this.setVisible(true);

		// Akce pro odeslání formuláře
		ActionListener submit = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submit(prop, name, secret, false);
			}
		};

		// Při stisku klávesy Enter odešle formulář
		name.addActionListener(submit);
		secret.addActionListener(submit);
		// Při změně pole pro heslo se vstup okamžitě zformátuje
		final Runnable sanitizer = new Runnable() {
			@Override
			public void run() {
				sanitize(secret);

			}
		};
		secret.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(sanitizer);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		// Při zavření okna odešle formulář
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// Ignorovat

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// Ignorovat

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// Ignorovat

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// Ignorovat

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Odeslat
				submit(prop, name, secret, true);

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// Ignorovat

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// Ignorovat

			}
		});
	}

	/**
	 * Odešle nový záznam
	 * 
	 * @param prop
	 *            Properties
	 * @param name
	 *            Textové pole s jménem
	 * @param secret
	 *            Textové pole s heslem
	 * @param exitOnError
	 *            Zavře okno i při chybě, pokud uživatel klikne na křížek
	 */
	private void submit(final Properties prop, final JTextField name, final JTextField secret, boolean exitOnError) {
		System.out.printf("Jméno: %s | Heslo: %s\n", name.getText(), secret.getText());
		boolean error = false;
		sanitize(secret);
		if (name.getText().equals("") || secret.getText().equals("") || !verify(secret)) {
			System.out.println("Nepřidáno");
			error = true;
		} else {
			System.out.printf("Base32 heslo je: %s\n", new Base32().encodeToString(secret.getText().getBytes()));
			int id = prop.size();
			// Po odstranění může být některý index přeskočen
			while (prop.containsKey(String.valueOf(id))) {
				id++;
			}
			StringBuilder sb = new StringBuilder();
			// Záznam je ve tvaru "jméno;heslo"
			sb.append(name.getText());
			sb.append(";");
			sb.append(secret.getText());
			prop.setProperty(String.valueOf(id), sb.toString());
			App.saveProperties();
			App.loadProperties();
		}
		if (exitOnError || !error) {
			this.dispose();
		}
	}

	/**
	 * Ověří zda je sdílené heslo platné
	 * 
	 * @param text
	 * @return platnost
	 */
	private boolean verify(JTextField text) {
		System.out.println("Testuji retezec " + text.getText());
		// Pokud třída Base32 vrátí klíč s nulovou délkou, není to platný klíč
		if (new Base32().decode(text.getText().getBytes()).length == 0) {
			System.out.println("Klíč není platný");
			// Zvyraznim okraje
			text.setBorder(new EtchedBorder(new Color(255, 0, 0), new Color(250, 50, 50)));
			return false;
		}
		System.out.println("Klíč je platný");
		return true;
	}

	/**
	 * Naformátuje sdílené heslo
	 * 
	 * @param secret
	 *            textové pole se sdíleným heslem
	 * @return
	 */
	private void sanitize(final JTextField secret) {
		// Velká písmena a odstranění mezer
		String text = secret.getText().toUpperCase().trim().replaceAll(" ", "");
		secret.setText(text);
	}
}
