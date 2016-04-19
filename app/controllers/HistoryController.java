package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.history.*;

import models.*;

import org.pac4j.play.java.RequiresAuthentication;


public class HistoryController extends Controller {
	@RequiresAuthentication(clientName = "CasClient")
	@Transactional
	public Result index() {
		return ok(index.render());
	}

	@Transactional
	public Result show(Integer id) {
		return ok(show.render(History.findById(id)));
	}
}