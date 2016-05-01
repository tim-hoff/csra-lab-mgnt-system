package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import org.pac4j.play.java.UserProfileController;
import org.pac4j.core.profile.CommonProfile;
import org.jasig.cas.client.authentication.*;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.client.Client;

import views.html.types.*;

import models.*;
import models.User;

import org.pac4j.play.java.RequiresAuthentication;

public class TypeController extends UserProfileController<CommonProfile> {

	@Transactional(readOnly=true)
	public Result edit() {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Types> typesForm = form(Types.class);
		return ok(edit.render(typesForm));
	}
	@Transactional
	public Result save() {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Types> typesForm = form(Types.class).bindFromRequest();

		if(typesForm.hasErrors()) {
			return badRequest(edit.render(typesForm));
		}
		typesForm.get().save();
		flash("success", "Type " + typesForm.get().type_name + " has been created");
		return ok(edit.render(typesForm));
	}
	@Transactional
	public Result delete() {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		Form<Types> typesForm = form(Types.class).bindFromRequest();

		if(typesForm.hasErrors()) {
			return badRequest(edit.render(typesForm));
		}
		typesForm.get().delete();
		flash("success", "Type has been deleted");
		return ok(edit.render(typesForm));
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