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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AddPanel extends JPanel {

	public AddPanel() {
		this.setBackground(new Color(100, 100, 100));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		
		JButton plus = new JButton("+");
		plus.setFont(plus.getFont().deriveFont(App.FONT_SIZE));
		plus.setBackground(App.COLOR);
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(plus, c);
		
		plus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pridat novy zaznam");
				
			}
		});
	}

}
