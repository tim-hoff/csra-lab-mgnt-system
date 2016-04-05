package controllers;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.CommonProfile;

import org.pac4j.play.java.UserProfileController;
import play.*;
import play.mvc.*;

import views.html.*;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class LoginController extends UserProfileController<CommonProfile>  {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result login() {
    	final CommonProfile profile = getUserProfile();

        return ok(login.render(profile));
    }

}