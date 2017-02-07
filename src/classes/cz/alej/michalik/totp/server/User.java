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

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Base32;
import org.json.simple.JSONObject;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import cz.alej.michalik.totp.util.OTPFactory;

/**
 * Správa jednotlivého uživatele
 * 
 * @author Petr Michalík
 *
 */
public class User extends ServerResource {

	// Uživatelské jméno
	String user;
	// JSON zpráva vrácena každou metodou
	JSONObject msg;
	// HTTP kódy
	Status error = new Status(404);
	Status success = new Status(200);
	Status created = new Status(201);

	/**
	 * Získá uživatelské jméno obsažené v URI
	 */
	public void doInit() {
		// Pokud metoda nezmění stav, stala se chyba
		msg = new JSONObject();
		msg.put("status", "error");

		this.user = getAttribute("id");
		if (!Data.exists(user)) {
			user = null;
			msg.put("exists", "0");
			msg.put("message", "User does not exist");
		} else {
			msg.put("exists", "1");
		}
	}

	/**
	 * Ověří TOTP kód
	 * 
	 * @param pass
	 *            TOTP heslo poslané pomocí POST
	 * @return JSON
	 */
	@Post
	public String validate(String pass) {
		this.setStatus(error);
		msg.put("valid", "false");
		if (user != null && pass != null) {
			// Sdilene heslo
			byte[] secret = new Base32().decode(Data.get(user).getBytes());
			// Vygeneruji hesla v časovém rozsahu
			List<String> codes = getAllCodes(secret, 1);
			msg.put("status", "ok");
			if (codes.contains(pass)) {
				this.setStatus(success);
				msg.put("valid", "true");
			} else {
				this.setStatus(new Status(401));
				msg.put("valid", "false");
			}
		}
		return msg.toJSONString();

	}

	/**
	 * Vygeneruje všechny kódy v určitém časovém rozsahu
	 * 
	 * @param secret
	 *            sdílené heslo
	 * @param window
	 *            časový rozsah
	 * @return List hesel
	 */
	private List<String> getAllCodes(byte[] secret, int window) {
		List<String> codes = new LinkedList<String>();
		for (int i = -window; i <= window; i++) {
			long step = Serve.step;
			String code = new OTPFactory().getOTP("TOTP", secret).setCounter(i * step).toString();
			codes.add(code);
		}
		return codes;
	}

	/**
	 * Informace o uživateli
	 * 
	 * @return JSON
	 */
	@Get
	public String getUser() {
		this.setStatus(error);
		if (user != null) {
			msg.put("status", "ok");
			msg.put("secret", Data.get(user));
			this.setStatus(success);
		}
		return msg.toJSONString();
	}

	/**
	 * Vygeneruje nové heslo pro stávajícího uživatele
	 * 
	 * @return JSON
	 */
	@Put
	public String update() {
		this.setStatus(error);
		if (user != null) {
			String secret = Users.generateSecret();
			Data.add(user, secret, true);
			msg.put("status", "ok");
			msg.put("secret", secret);
			this.setStatus(created);
			this.setLocationRef("./" + user);
		}
		return msg.toJSONString();
	}

	/**
	 * Vymaže uživatele
	 */
	@Delete
	public StringRepresentation delete() {
		this.setStatus(error);
		if (user != null) {
			Data.remove(user);
			msg.put("status", "ok");
			this.setStatus(success);
		}
		return new StringRepresentation(msg.toJSONString());
	}

}
