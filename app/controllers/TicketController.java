package controllers;

import play.Logger;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import java.util.*;

import views.html.ticket.*;
import views.html.ticket.reports.*;
import models.*;

import scala.collection.JavaConverters;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.play.java.RequiresAuthentication;
import org.pac4j.play.java.UserProfileController;

public class TicketController extends UserProfileController<CommonProfile> {

	// @RequiresAuthentication(clientName = "CasClient")
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

		List<String> data = JPA.em()
		.createQuery("Select t.category_name From Categories t")
		.getResultList();
		
		Form<Ticket> ticketForm = form(Ticket.class);
		return ok(create.render(ticketForm,User.findById(getUserProfile().getId()), data));
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

		List<String> data = JPA.em()
		.createQuery("Select t.category_name From Categories t")
		.getResultList();
		
		Form<Ticket> ticketForm = form(Ticket.class).fill(
			Ticket.findById(id));
		return ok(edit.render(id, ticketForm, data));
	}

	@Transactional
	public Result update(Integer id) {
		if(!checkPrivileges())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/tickets");
		}

		List<String> data = JPA.em()
		.createQuery("Select t.category_name From Categories t")
		.getResultList();
		
		Form<Ticket> ticketForm = form(Ticket.class).bindFromRequest();
		if(ticketForm.hasErrors()) {
			return badRequest(edit.render(id, ticketForm, data));
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
		List<String> data = JPA.em()
		.createQuery("Select t.category_name From Categories t")
		.getResultList();
		
		Form<Ticket> ticketForm = form(Ticket.class).bindFromRequest();

		if(ticketForm.hasErrors()) {
			return badRequest(create.render(ticketForm,User.findById(getUserProfile().getId()), data));
		}

		ticketForm.get().save();
		if(ticketForm.get().assigned_to != null){
		flash("success", "Ticket " + ticketForm.get().name + " has been created and assigned to admin " + ticketForm.get().assigned_to);
		}
		else{
		flash("success", "Ticket " + ticketForm.get().name + " has been created and assigned to an admin.");
		}
		return ok(index.render(User.findById(getUserProfile().getId())));
	}


	@Transactional
	public Result report_1(Integer id) {
		if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	
		List<String> data = JPA.em()
		.createQuery("Select t.category_name From Categories t")
		.getResultList();

		for (String cat : data) {
			hmap.put(cat, models.Ticket.categoryCount(cat, id));
		}
		
		return ok(report_1.render(id, hmap));
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