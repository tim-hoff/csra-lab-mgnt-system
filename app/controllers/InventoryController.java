package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.*;

import models.*;

public class InventoryController extends Controller {
@Transactional
public Result view(Integer id) {
		return ok(inventory.render(Inventory.findById(id)));
	}

@Transactional
public Result index() {
		return ok(inventorys.render());
	}
}