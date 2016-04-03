package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.inventory.*;

import models.*;

public class InventoryController extends Controller {
	@Transactional
	public Result index() {
		return ok(index.render());
	}

	@Transactional
	public Result show(Integer id) {
		return ok(show.render(Inventory.findById(id)));
	}

	@Transactional
	public Result edit(Integer id) {
			Form<Inventory> invForm = form(Inventory.class).fill(
					Inventory.findById(id));
			return ok(edit.render(id, invForm));
	}

	@Transactional
	public Result update(Integer id) {
		Form<Inventory> invForm = form(Inventory.class);
		Form<Inventory> filledForm = invForm.bindFromRequest();
		if(filledForm.hasErrors()) {
		    return badRequest(edit.render(id, invForm));
		} else {
		    filledForm.get().update(id);
		    flash("success", "Item " + id + " has been updated");
		    return ok(show.render(Inventory.findById(id)));
		}		
	}
}