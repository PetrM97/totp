package cz.alej.michalik.totp.server;

import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class Serve {
	
	public static void main(String[] args) throws Exception{
		Component c = new Component();
		c.getServers().add(Protocol.HTTP, 8080);
		c.getDefaultHost().attach("", createInboundRoot());
		
		c.start();
	}
	
	public static Restlet createInboundRoot() {
	    Router router = new Router();
	    router.attach("/user",Users.class);
	    router.attach("/user/{id}",User.class);

	    return router;
	}

}
