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
package cz.alej.michalik.totp.server;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import org.apache.commons.codec.binary.Base32;
import org.json.simple.JSONObject;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class Users extends ServerResource {

	Properties p = Data.load();
	String user = null;

	/**
	 * Vytvoří nový záznam a vrátí index záznamu a klíč
	 * 
	 * @return JSON
	 */
	@Post
	public String create(String user) {
		// Zpráva pro klienta je JSON
		JSONObject msg = new JSONObject();
		// Pokud se status nezmění, nastala chyba
		this.setStatus(new Status(400));
		msg.put("status", "error");
		// Uživatelské jméno smí obsahovat pouze písmena, čísla nebo podtržítka
		if (user.matches("^user=[a-zA-Z0-9_]+$") == false) {
			this.setStatus(new Status(400));
			msg.put("message", "Data should be 'user=[username]'");
		} else {
			// Získám uživatelské jméno
			user = user.replace("user=", "");
			// Vygeneruju pseudonáhodné heslo
			String secret = generateSecret();
			// Pokud existuje uživatelské jméno
			if (!Data.add(user, secret, false)) {
				this.setStatus(new Status(409));
				msg.put("message", "Username already exists");
			} else {
				this.setStatus(new Status(201));
				msg.put("status", "ok");
				msg.put("username", user);
				msg.put("secret", secret);
			}
		}
		return msg.toJSONString();
	}

	/**
	 * Vrátí počet uživatelů
	 * 
	 * @return JSON
	 */
	@Get
	public String getUsers() {
		// Zpráva pro klienta je JSON
		JSONObject msg = new JSONObject();
		this.setStatus(new Status(200));
		p = Data.load();
		msg.put("status", "ok");
		msg.put("users", String.valueOf(p.size()));
		return msg.toJSONString();
	}

	/**
	 * Vymaže všechny záznamy
	 */
	@Delete
	public StringRepresentation delete() {
		JSONObject msg = new JSONObject();
		Data.deleteAll();
		msg.put("status", "ok");
		return new StringRepresentation(msg.toJSONString());
	}

	/**
	 * Vygenerovat klíč pro generování TOTP hesla
	 * 
	 * @return base32 řetězec
	 */
	public static String generateSecret() {
		// Chci klíč o velikosti 20 bytů
		int maxBits = 20 * 8 - 1;
		SecureRandom rand = new SecureRandom();
		byte[] val = new BigInteger(maxBits, rand).toByteArray();
		return new Base32().encodeToString(val);
	}

}
