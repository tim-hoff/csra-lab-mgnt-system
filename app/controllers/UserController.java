package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.user.*;

import models.*;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.play.java.RequiresAuthentication;
import org.pac4j.play.java.UserProfileController;


public class UserController extends UserProfileController<CommonProfile> {
	
	@RequiresAuthentication(clientName = "CasClient") 
	@Transactional
	public Result index() {
		if(!checkPrivilegesAdmin())
		{
			flash("Insufficient Privileges");
			return ok(views.html.index.render());
		}
		
		return ok(index.render());
	}

	@Transactional
	public Result show(String id) {
		if(!checkPrivilegesAdmin())
		{
			flash("Insufficient Privileges");
			return ok(views.html.index.render());
		}
		
		return ok(show.render(User.findById(id)));
	}

	@Transactional
	public Result create() {
		if(!checkPrivilegesAdmin())
		{
			flash("Insufficient Privileges");
			return ok(views.html.index.render());
		}
		
		Form<User> userForm = form(User.class);
		return ok(create.render(userForm));
	}

	@Transactional
	public Result delete(String id) {
		if(!checkPrivilegesAdmin())
		{
			flash("Insufficient Privileges");
			return ok(views.html.index.render());
		}
		
		User.findById(id).delete();
		flash("success", "User item has been deleted");
		return ok(index.render());
	}

	@Transactional 
	public Result save() {
		if(!checkPrivileges())
		{
			flash("Insufficient Privileges");
			return ok(views.html.index.render());
		}
		
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
		//Admins cannot edit other admins or SuperAdmins account, must be super admin, checked here
		if(!checkPrivilegesAdmin() || 
				(User.findById(getUserProfile().getId()).role == User.Role.Admin && User.findById(id).role == User.Role.Admin) ||
				(User.findById(getUserProfile().getId()).role == User.Role.Admin && User.findById(id).role == User.Role.SuperAdmin))
		{
			flash("Insufficient Privileges");
			return ok(views.html.index.render());
		}
		
		Form<User> userForm = form(User.class).fill(
			User.findById(id));
		return ok(edit.render(id, userForm));
	}

	@Transactional
	public Result update(String id) {
		if(!checkPrivilegesAdmin())
		{
			flash("Insufficient Privileges");
			return ok(views.html.index.render());
		}
		
		Form<User> userForm = form(User.class).bindFromRequest();
		if(userForm.hasErrors()) {
			return badRequest(edit.render(id, userForm));
		} else {
			userForm.get().update(id);
			flash("success", "User " + id + " has been updated");
			return ok(show.render(User.findById(id)));
		}
	}
	
	//This function returns false if current user does not possess any roles
	public boolean checkPrivileges()
	{
		CommonProfile profile = getUserProfile();
		//if you don't have admin role then redirect back to dashboard
		if((User.findById(profile.getId()).role == null))
		{
			return false;
		}
		return true;
	}
	//This function returns false if current user does not possess the role Admin or SuperAdmin
	public boolean checkPrivilegesAdmin()
	{
		CommonProfile profile = getUserProfile();
		//if you don't have admin role then redirect back to dashboard
		if(User.findById(profile.getId()).role == User.Role.Admin ||  User.findById(profile.getId()).role == User.Role.SuperAdmin)
		{
			return true;
		}
		return false;
	}
}