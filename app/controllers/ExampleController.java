package controllers;
import play.*;
import play.mvc.*;
import views.html.*;
/**
* This controller contains an action to handle HTTP requests
* to the application's home page.
*/
public class ExampleController extends Controller {
	public Result noscript() {
		return ok(noscript.render());
	}

	public Result script() {
		return ok(script.render());
	}
}