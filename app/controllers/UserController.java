package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.user.*;

import models.*;

import org.pac4j.play.java.RequiresAuthentication;

public class UserController extends Controller {
	@Transactional
	//@RequiresAuthentication(clientName = "CasClient")
	public Result index() {
		return ok(index.render());
	}

	@Transactional
	public Result show(String id) {
		return ok(show.render(User.findById(id)));
	}

	@Transactional
	public Result create() {
		Form<User> userForm = form(User.class);
		return ok(create.render(userForm));
	}

	@Transactional
	public Result delete(String id) {
		User.findById(id).delete();
		flash("success", "User item has been deleted");
		return ok(index.render());
	}

	@Transactional 
	public Result save() {
		Form<User> userForm = form(User.class).bindFromRequest();
		if(userForm.hasErrors()){
			return badRequest(create.render(userForm));
		} 
		userForm.get().save();
		flash("success", "User " + userForm.get().user_id +" has been created");
		return ok(index.render());
	}

	@Transactional(readOnly=true)
	public Result edit(String id) {
		Form<User> userForm = form(User.class).fill(
			User.findById(id));
		return ok(edit.render(id, userForm));
	}

	@Transactional
	public Result update(String id) {
		Form<User> userForm = form(User.class).bindFromRequest();
		if(userForm.hasErrors()) {
			return badRequest(edit.render(id, userForm));
		} else {
			userForm.get().update(id);
			flash("success", "User " + id + " has been updated");
			return ok(show.render(User.findById(id)));
		}
	}
}