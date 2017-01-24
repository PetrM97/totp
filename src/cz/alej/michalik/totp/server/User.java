package cz.alej.michalik.totp.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class User extends ServerResource{
	
	String user;
	
	public void doInit() {
        this.user = getAttribute("id");
    }
	
	@Get("txt")  
	public String getUser() {  
	   return "Returning info about " + this.user;  
	}

}
