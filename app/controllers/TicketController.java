package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.ticket.*;

import models.*;

public class TicketController extends Controller {
	@Transactional
	public Result index() {
		return ok(index.render());
	}

	@Transactional
	public Result show(Integer id) {
		return ok(show.render(Ticket.findById(id)));
	}

	@Transactional
	public Result create() {
		Form<Ticket> ticketForm = form(Ticket.class);
		return ok(create.render(ticketForm));
	}

	@Transactional
	public Result save() {
		Form<Ticket> ticketForm = form(Ticket.class).bindFromRequest();

		if(ticketForm.hasErrors()) {
			return badRequest(create.render(ticketForm));
		}
		ticketForm.get().save();
		flash("success", "Ticket " + ticketForm.get().name + " has been created");
		return ok(index.render());
	}
	
}