package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.*;

import models.*;

public class UserController extends Controller {
@Transactional
public Result view(String id) {
		return ok(user.render(User.findById(id)));
	}

@Transactional
public Result index() {
		return ok(users.render());
	}
}