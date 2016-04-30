package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.ticket.*;
import views.html.ticket.reports.*;
import models.*;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.play.java.RequiresAuthentication;
import org.pac4j.play.java.UserProfileController;

public class TicketController extends UserProfileController<CommonProfile> {

	@RequiresAuthentication(clientName = "CasClient")
	@Transactional
	public Result index() {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/tickets");
		}
		
		return ok(index.render(User.findById(getUserProfile().getId())));
	}

	@Transactional
	public Result show(Integer id) {
		
		if(checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/tickets");
		}
		
		return ok(show.render(Ticket.findById(id)));
	}

	@Transactional
	public Result create() {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/tickets");
		}
		
		Form<Ticket> ticketForm = form(Ticket.class);
		return ok(create.render(ticketForm));
	}

	@Transactional
	public Result delete(Integer id) {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		Ticket.findById(id).delete();
		flash("success", "Ticket item has been deleted");
		return ok(index.render(User.findById(getUserProfile().getId())));
	}

	@Transactional(readOnly=true)
	public Result edit(Integer id) {
		if(!checkPrivilegesAdmin() || Ticket.findById(id).created_for == getUserProfile().getId())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		Form<Ticket> ticketForm = form(Ticket.class).fill(
			Ticket.findById(id));
		return ok(edit.render(id, ticketForm));
	}

	@Transactional
	public Result update(Integer id) {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/tickets");
		}
		
		Form<Ticket> ticketForm = form(Ticket.class).bindFromRequest();
		if(ticketForm.hasErrors()) {
			return badRequest(edit.render(id, ticketForm));
		} else {
			ticketForm.get().update(id);
			flash("success", "Item " + id + " has been updated");
			return ok(show.render(Ticket.findById(id)));
		}
	}

	@Transactional
	public Result save() {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/tickets");
		}
		
		Form<Ticket> ticketForm = form(Ticket.class).bindFromRequest();

		if(ticketForm.hasErrors()) {
			return badRequest(create.render(ticketForm));
		}
		ticketForm.get().save();
		flash("success", "Ticket " + ticketForm.get().name + " has been created
				and assigned to admin " + ticketForm.get().assigned_to);
		return ok(index.render(User.findById(getUserProfile().getId())));
	}

	@Transactional
	public Result report_1(Integer id) {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(report_1.render(id));
	}

	@Transactional
	public Result report_2(Integer id){
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(report_2.render(id));
	}

	//@Transactional
	//public Result getReport(Integer time) {
		// test
	//}
	
	
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