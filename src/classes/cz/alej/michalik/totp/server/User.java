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

public class User extends ServerResource {

	String user;

	public void doInit() {
		this.user = getAttribute("id");
		if (!Data.exists(user)) {
			user = null;
		}
	}

	@Post
	public String validate(String pass) {
		// Odpoved
		JSONObject msg = new JSONObject();
		this.setStatus(new Status(404));
		msg.put("status", "error");
		msg.put("valid", "false");
		if (user != null && pass != null) {
			// Sdilene heslo
			byte[] secret = new Base32().decode(Data.get(user).getBytes());
			// Vygeneruji hesla v časovém rozsahu
			List<String> codes = getAllCodes(secret, 1);
			msg.put("status", "ok");
			if (codes.contains(pass)) {
				this.setStatus(new Status(200));
				msg.put("valid", "true");
			} else {
				this.setStatus(new Status(401));
				msg.put("valid", "false");
			}
		}
		return msg.toJSONString();

	}

	private List<String> getAllCodes(byte[] secret, int window) {
		List<String> codes = new LinkedList<String>();
		for (int i = -window; i <= window; i++) {
			String code = new OTPFactory().getOTP("TOTP", secret).setCounter(i * 30).toString();
			codes.add(code);
		}
		return codes;
	}

	@Get
	public String getUser() {
		JSONObject msg = new JSONObject();
		this.setStatus(new Status(404));
		msg.put("status", "error");
		if (user != null) {
			msg.put("status", "ok");
			msg.put("secret", Data.get(user));
			this.setStatus(new Status(200));
		}
		return msg.toJSONString();
	}

	@Put
	public String update() {
		JSONObject msg = new JSONObject();
		this.setStatus(new Status(404));
		msg.put("status", "error");
		if (user != null) {
			String secret = Users.generateSecret();
			Data.add(user, secret, true);
			msg.put("status", "ok");
			msg.put("secret", secret);
			this.setStatus(new Status(201));
		}
		return msg.toJSONString();
	}

	@Delete
	public StringRepresentation delete() {
		JSONObject msg = new JSONObject();
		this.setStatus(new Status(404));
		msg.put("status", "error");
		if (user != null) {
			Data.remove(user);
			msg.put("status", "ok");
			this.setStatus(new Status(200));
		}
		return new StringRepresentation(msg.toJSONString());
	}

}
