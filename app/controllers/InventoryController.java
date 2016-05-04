package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import org.apache.commons.mail.EmailAttachment;
import play.Play;
import play.libs.mailer.MailerClient;
import play.libs.mailer.Email;
import javax.inject.Inject;
import java.io.File;


import org.pac4j.play.java.UserProfileController;
import org.pac4j.core.profile.CommonProfile;
import org.jasig.cas.client.authentication.*;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.client.Client;
import org.joda.time.*;

import views.html.inventory.*;

import models.*;
import models.User;

import org.pac4j.play.java.RequiresAuthentication;

public class InventoryController extends UserProfileController<CommonProfile> {

    private final MailerClient mailer;

    @Inject
    public InventoryController(MailerClient mailer) {
    this.mailer = mailer;
    }

	// @RequiresAuthentication(clientName = "CasClient")
	@Transactional
	public Result index(){
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(index.render(getUserProfile().getId(), "All"));
<<<<<<< HEAD
=======
	}
	@Transactional
	public Result select(){
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(categoryIndex.render(getUserProfile().getId()));
	}
	@Transactional
	public Result filtered(String type) {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(index.render(getUserProfile().getId(), type));
>>>>>>> 4453f22c312530d97d290e617c9ee1b2d8fee93e
	}
	@Transactional
	public Result select(){
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(categoryIndex.render(getUserProfile().getId()));
	}
	@Transactional
	public Result filtered(String type) {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(index.render(getUserProfile().getId(), type));
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
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		Form<Inventory> invForm = form(Inventory.class).fill(
			Inventory.findById(id));
		return ok(edit.render(id, invForm));
	}
	
	@Transactional
	public Result checkout(Integer id) {
		Inventory item = Inventory.findById(id);
		
		if(item.available() && !checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		item.taken_date = new DateTime();
		item.rented_by = User.findById(getUserProfile().getId());
		//item.return_date = new DateTime().plusWeeks(1);
		
		Form<Inventory> invForm = form(Inventory.class).fill(item);
<<<<<<< HEAD
		return ok(checkout.render(item));
>>>>>>> 4453f22c312530d97d290e617c9ee1b2d8fee93e
	}

	@Transactional
	public Result checkin(Integer id) {
		Inventory item = Inventory.findById(id);
		
		if(item.rented_by != null && item.rented_by.user_id == getUserProfile().getId() && !checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/items");
		}
		
		item.return_date = new DateTime();
		item.rented_by = null;
		
		Form<Inventory> invForm = form(Inventory.class).fill(item);
		//so two updates need to occur, one to fire off the history table trigger
		invForm.get().update(id);
		//second to null both the return date and taken date.
		item.return_date = null;
		item.taken_date = null;
		
		invForm = form(Inventory.class).fill(item);
		invForm.get().update(id);
		
		flash("success", "Inventory item has been returned");

		return select();
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
		return select();
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
		return select();
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