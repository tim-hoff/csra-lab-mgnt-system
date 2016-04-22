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


import views.html.inventory.*;

import models.*;

import org.pac4j.play.java.RequiresAuthentication;

public class InventoryController extends Controller {

    private final MailerClient mailer;

    @Inject
    public InventoryController(MailerClient mailer) {
    this.mailer = mailer;
    }
	

	@Transactional
	//@RequiresAuthentication(clientName = "CasClient")
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
	@Transactional
	public Result mail() {
		Form<Inventory> invForm = form(Inventory.class);
		return ok(mail.render(invForm));
	}
	@Transactional
	public Result mailOverdue() {
		List<User> data = JPA.em()
        .createQuery("Select u.user_id From Inventory i, User u Where Now() > i.return_date AND i.return_date is not null AND i.rented_by = u.user_id")
        .getResultList();
		for (int i = 0; i < data.size(); i++) {
         final Email emailer = new Email();
         emailer.setSubject("test");
         emailer.setFrom("<csralabmailtest@gmail.com>");
         emailer.addTo("<n2i5p5y6g1c6a4x5@csra-lab.slack.com>");
         emailer.setBodyText(data.get(i) + "@latech.edu" + "\n\n  TESTTTTT");
         mailer.send(emailer);
      }
		Form<Inventory> invForm = form(Inventory.class);
		return ok(mail.render(invForm));
	}
}