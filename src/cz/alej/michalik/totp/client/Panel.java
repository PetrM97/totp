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

import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	
	public Panel(Properties p){
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Pro získání indexu
		for (Entry<Object, Object> entry : p.entrySet()) {
			// Pro každý záznam vytvoří panel
			this.add(new OtpPanel( (String) entry.getValue(), p, Integer.valueOf((String) entry.getKey()) ));
			System.out.println(entry.getKey() + " | " + entry.getValue());
			this.add(new JSeparator());
		}
		
		// Přidá panel pro přidání nových záznamů
		this.add(new AddPanel(p));
	
	}

}
