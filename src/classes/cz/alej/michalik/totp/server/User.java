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

import org.json.simple.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class User extends ServerResource {

	String user;

	public void doInit() {
		this.user = getAttribute("id");
	}

	@Get
	public String getUser() {
		JSONObject msg = new JSONObject();
		msg.put("status", "ok");
		msg.put("secret", Data.get(user));
		return msg.toJSONString();
	}

}
