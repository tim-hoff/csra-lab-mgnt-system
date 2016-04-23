package controllers;

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

import views.html.inventory.*;

import models.*;
import models.User;

import org.pac4j.play.java.RequiresAuthentication;

public class InventoryController extends UserProfileController<CommonProfile> {

	@RequiresAuthentication(clientName = "CasClient")
	@Transactional
	public Result index(){
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(index.render());
	}

	@Transactional
	public Result show(Integer id) {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		return ok(show.render(Inventory.findById(id)));
	}

	@Transactional(readOnly=true)
	public Result edit(Integer id) {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Inventory> invForm = form(Inventory.class).fill(
			Inventory.findById(id));
		return ok(edit.render(id, invForm));
	}

	@Transactional(readOnly=true)
	public Result create() {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Inventory> invForm = form(Inventory.class);
		return ok(create.render(invForm));
	}
	@Transactional
	public Result save() {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Inventory> invForm = form(Inventory.class).bindFromRequest();

		if(invForm.hasErrors()) {
			return badRequest(create.render(invForm));
		}
		invForm.get().save();
		flash("success", "Inventory " + invForm.get().item_id + " has been created");
		return ok(index.render());
	}
	@Transactional
	public Result delete(Integer id) {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Inventory.findById(id).delete();
		flash("success", "Inventory item has been deleted");
		return ok(index.render());
	}
	@Transactional
	public Result update(Integer id) {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Inventory> invForm = form(Inventory.class).bindFromRequest();
		if(invForm.hasErrors()) {
			return badRequest(edit.render(id, invForm));
		} else {
			invForm.get().update(id);
			flash("success", "Item " + id + " has been updated");
			return ok(show.render(Inventory.findById(id)));
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