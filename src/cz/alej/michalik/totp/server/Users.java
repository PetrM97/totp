package cz.alej.michalik.totp.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class Users extends ServerResource {
	
	@Get("txt")  
	public String getUser() {  
	   return "Returning info about all users";  
	}

}
