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

import javax.swing.JButton;
import javax.swing.JPanel;
import cz.alej.michalik.totp.utility.TOTP;

@SuppressWarnings("serial")
public class OtpPanel extends JPanel {

	public OtpPanel(String raw_data) {
		final String[] data = raw_data.split(";");
		System.out.printf("0: %s | 1: %s\n", data[0],data[1]);
		this.setBackground(App.COLOR);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		
		JButton passPanel = new JButton("000000");
		passPanel.setFont(passPanel.getFont().deriveFont(App.FONT_SIZE));
		passPanel.setBackground(App.COLOR);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 5;
		this.add(passPanel,c);
		passPanel.setText(data[0]);

		JButton but = new JButton("Upravit");
		but.setFont(but.getFont().deriveFont(App.FONT_SIZE));
		but.setBackground(App.COLOR);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.EAST;
		this.add(but,c);
		
		passPanel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.printf("Vygenerovany klic %s zkopirovat do schranky\n", new TOTP(data[1].getBytes()).toString());
				
			}
		});
		
		but.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.printf("Upravit %s\n",data[0]);
				
			}
		});
		
	}

}
