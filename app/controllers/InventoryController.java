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

	@Transactional(readOnly=true)
	public Result edit(Integer id) {
		Form<Inventory> invForm = form(Inventory.class).fill(
			Inventory.findById(id));
		return ok(edit.render(id, invForm));
	}

	@Transactional(readOnly=true)
	public Result create() {
		Form<Inventory> invForm = form(Inventory.class);
		return ok(create.render(invForm));
	}
	@Transactional
	public Result save() {
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
		Inventory.findById(id).delete();
		flash("success", "Inventory item has been deleted");
		return ok(index.render());
	}
	@Transactional
	public Result update(Integer id) {
		Form<Inventory> invForm = form(Inventory.class).bindFromRequest();
		if(invForm.hasErrors()) {
			return badRequest(edit.render(id, invForm));
		} else {
			invForm.get().update(id);
			flash("success", "Item " + id + " has been updated");
			return ok(show.render(Inventory.findById(id)));
		}		
	}
}