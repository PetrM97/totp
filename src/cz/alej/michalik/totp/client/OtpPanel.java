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
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.codec.binary.Base32;

import cz.alej.michalik.totp.utility.TOTP;

/**
 * Panel pro záznam s TOTP kódem
 * 
 * @author Petr Michalík
 *
 */
@SuppressWarnings("serial")
public class OtpPanel extends JPanel {

	Clip clip = new Clip();

	public OtpPanel(String raw_data,final Properties p, final int index) {
		// Data jsou oddělena středníkem
		final String[] data = raw_data.split(";");

		this.setBackground(App.COLOR);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

		JButton passPanel = new JButton("");
		passPanel.setFont(passPanel.getFont().deriveFont(App.FONT_SIZE));
		passPanel.setBackground(App.COLOR);
		// Zabere většinu místa
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 5;
		this.add(passPanel, c);
		passPanel.setText(data[0]);

		JButton delete = new JButton("X");
		delete.setFont(delete.getFont().deriveFont(App.FONT_SIZE*1.5f));
		delete.setBackground(App.COLOR);
		// Zabere kousek vpravo
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.EAST;
		this.add(delete, c);

		passPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.set(new TOTP(new Base32().decode(data[1].getBytes())).toString());
				System.out.printf("Kód pro %s je ve schránce\n", data[0]);
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.printf("Odstraněn %s s indexem %d\n", data[0],index);
				p.remove(String.valueOf(index));
				App.saveProperties();
				App.loadProperties();
			}
		});

	}

}
