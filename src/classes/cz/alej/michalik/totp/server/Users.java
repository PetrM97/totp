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

import java.util.Properties;

import org.json.simple.JSONObject;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class Users extends ServerResource {

	Properties p = Data.load();

	@Get("txt")
	public String getUsers() {
		return "Returning info about all users";
	}

	@Get("json")
	public String getUsersJSON() {
		this.doError(new Status(501));
		JSONObject msg = new JSONObject();
		msg.put("status", "ok");
		return msg.toJSONString();
	}

	@Post
	public String create() {
		int key = Data.add("User");
		this.setStatus(new Status(201));
		JSONObject msg = new JSONObject();
		msg.put("status", "ok");
		msg.put("key", key);
		return msg.toJSONString();
	}

}
