package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.*;

import models.*;

public class TicketController extends Controller {
@Transactional
public Result view(Integer id) {
		return ok(ticket.render(Ticket.findById(id)));
	}
public Result index() {
		return ok(ticket.render(Ticket.findById(1)));
	}
}