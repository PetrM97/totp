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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JPanel;

/**
 * Třída pro vytvoření hlavního panelu
 * 
 * @author Petr Michalík
 *
 */

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	/**
	 * Přídá hlavní panel
	 * 
	 * @param p
	 *            Properties
	 */
	public MainPanel(Properties p) {

		this.setLayout(new GridBagLayout());
		// Parametry pro jednotlivé panely
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0.5;
		c.insets = new Insets(20, 10, 20, 10);
		c.gridy = 0;
		// Přidá každý záznam jako samostatný panel
		for (Entry<Object, Object> entry : p.entrySet()) {
			// Pro každý záznam vytvoří panel
			this.add(new OtpPanel((String) entry.getValue(), p, Integer.valueOf((String) entry.getKey())), c);
			System.out.println(entry.getKey() + " | " + entry.getValue());
			c.gridy += 1;
		}

		// Výplň pro nahuštění panelů
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 10;

		this.add(new JPanel(), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.SOUTH;
		c.weighty = 0.5;
		c.gridy = 99;
		// Přidá panel pro přidání nových záznamů
		this.add(new AddPanel(p), c);

	}

}
