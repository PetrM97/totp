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
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Přidá panel s pluskem pro přidání nového záznamu
 * 
 * @author Petr Michalík
 *
 */
@SuppressWarnings("serial")
public class AddPanel extends JPanel {

	private JFrame dialog = new JFrame("Přidat");

	/**
	 * Konstruktor přidání panelu
	 * 
	 * @param prop
	 *            Properties
	 */
	public AddPanel(final Properties prop) {
		this.setLayout(new GridBagLayout());
		// Mřížkové rozložení prvků
		GridBagConstraints c = new GridBagConstraints();
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

		JButton plus = new JButton("+");
		plus.setFont(plus.getFont().deriveFont(App.FONT_SIZE));
		plus.setBackground(App.COLOR);
		try {
			String path = "/material-design-icons/content/drawable-xhdpi/ic_add_circle_black_36dp.png";
			Image img = ImageIO.read(App.class.getResource(path));
			plus.setIcon(new ImageIcon(img));
			plus.setText("");
		} catch (Exception e) {
			System.out.println("Icon not found");
		}
		c.weightx = 1;
		// Roztáhne prvek na celou šířku
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(plus, c);

		// Akce po stisku tlačítka - vytvoří nové okno
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Okno
				dialog = new AddDialog(prop);

			}
		});
	}
}
