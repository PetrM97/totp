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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Přidá panel s pluskem pro přidání nového záznamu
 * 
 * @author Petr Michalík
 *
 */
@SuppressWarnings("serial")
public class AddPanel extends JPanel {

	public AddPanel(final Properties prop) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

		JButton plus = new JButton("+");
		plus.setFont(plus.getFont().deriveFont(App.FONT_SIZE));
		plus.setBackground(App.COLOR);
		c.weightx = 1;
		// Roztáhne prvek na celou šířku
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(plus, c);

		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pridat novy zaznam");
				JFrame dialog = new JFrame("Přidat");
				dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				dialog.setMinimumSize(new Dimension(600, 150));
				dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.X_AXIS));
				dialog.setLocationByPlatform(true);

				dialog.add(new JLabel("Název: "));
				final JTextField name = new JTextField();
				dialog.add(name);
				dialog.add(new JLabel("Heslo: "));
				final JTextField secret = new JTextField();
				dialog.add(secret);

				dialog.setVisible(true);

				dialog.addWindowListener(new WindowListener() {

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
						System.out.printf("Jméno: %s | Heslo: %s\n", name.getText(), secret.getText());
						int id = prop.size();
						// Po odstranění může být některý index přeskočen
						while( prop.containsKey(String.valueOf(id))){
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
		});
	}

}
