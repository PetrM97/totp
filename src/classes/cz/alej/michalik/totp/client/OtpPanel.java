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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.commons.codec.binary.Base32;

import cz.alej.michalik.totp.util.TOTP;

/**
 * Panel pro záznam s TOTP kódem
 * 
 * @author Petr Michalík
 *
 */
@SuppressWarnings("serial")
public class OtpPanel extends JPanel {

	Clip clip = new Clip();

	/**
	 * Přidá jeden panel se záznamem
	 * 
	 * @param raw_data
	 *            Data z Properties
	 * @param p
	 *            Properties
	 * @param index
	 *            Index záznamu - pro vymazání
	 */
	public OtpPanel(String raw_data, final Properties p, final int index) {
		// Data jsou oddělena středníkem
		final String[] data = raw_data.split(";");

		// this.setBackground(App.COLOR);
		this.setLayout(new GridBagLayout());
		// Mřížkové rozložení prvků
		GridBagConstraints c = new GridBagConstraints();
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

		// Tlačítko pro zkopírování hesla
		final JButton passPanel = new JButton("");
		passPanel.setFont(passPanel.getFont().deriveFont(App.FONT_SIZE));
		passPanel.setBackground(App.COLOR);
		// Zabere celou šířku
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 100;
		this.add(passPanel, c);
		passPanel.setText(data[0]);

		// Tlačítko pro smazání
		JButton delete = new JButton("X");
		try {
			String path = "/material-design-icons/action/drawable-xhdpi/ic_delete_black_24dp.png";
			Image img = ImageIO.read(App.class.getResource(path));
			delete.setIcon(new ImageIcon(img));
			delete.setText("");
		} catch (Exception e) {
			System.out.println("Icon not found");
		}
		delete.setFont(delete.getFont().deriveFont(App.FONT_SIZE));
		delete.setBackground(App.COLOR);
		// Zabere kousek vpravo
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.EAST;
		this.add(delete, c);

		// Akce pro vytvoření a zkopírování hesla do schránky
		passPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Generuji kod pro " + data[1]);
				System.out.println(new Base32().decode(data[1].getBytes()).length);
				clip.set(new TOTP(new Base32().decode(data[1].getBytes())).toString());
				System.out.printf("Kód pro %s je ve schránce\n", data[0]);
				passPanel.setText("Zkopírováno");
				// Zobrazí zprávu na 1 vteřinu
				int time = 1000;
				// Animace zobrazení zprávy po zkopírování
				final Timer t = new Timer(time, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						passPanel.setText(data[0]);
					}
				});
				t.start();
				t.setRepeats(false);
			}
		});

		// Akce pro smazání panelu a uložení změn
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.printf("Odstraněn %s s indexem %d\n", data[0], index);
				p.remove(String.valueOf(index));
				App.saveProperties();
				App.loadProperties();
			}
		});

	}

}
