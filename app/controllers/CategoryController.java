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

import views.html.categories.*;

import models.*;
import models.User;

import org.pac4j.play.java.RequiresAuthentication;

public class CategoryController extends UserProfileController<CommonProfile> {

	@Transactional(readOnly=true)
	public Result edit() {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Categories> categoriesForm = form(Categories.class);
		return ok(edit.render(categoriesForm));
	}
	@Transactional
	public Result save() {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Categories> categoriesForm = form(Categories.class).bindFromRequest();

		if(categoriesForm.hasErrors()) {
			return badRequest(edit.render(categoriesForm));
		}
		categoriesForm.get().save();
		flash("success", "  The category " + categoriesForm.get().category_name + " has been created.");
		return ok(edit.render(categoriesForm));
	}
	@Transactional
	public Result delete(String id) {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}

		Categories.findById(id).delete();
		flash("success", "  The category has been deleted.");

		Form<Categories> categoriesForm = form(Categories.class);
		return ok(edit.render(categoriesForm));
	}
	@Transactional
	public static List<String> catList() {
		List<String> data = JPA.em()
		.createQuery("Select t.category_name From Categories t")
		.getResultList();
		return data;
	}

	@Override
	 public CommonProfile getUserProfile(){
	 		CommonProfile com = new CommonProfile();
	 		com.setId("box");
	 		return com;
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