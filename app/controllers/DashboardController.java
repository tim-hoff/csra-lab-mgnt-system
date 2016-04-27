package controllers;

import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import org.pac4j.play.java.RequiresAuthentication;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
 
public class DashboardController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	@RequiresAuthentication(clientName = "CasClient")
	@Transactional
    public Result index() {
        return ok(dashboard.render());
    }

}
