package controllers;

import static play.data.Form.*;
import org.apache.commons.mail.EmailAttachment;
import play.Play;
import play.libs.mailer.MailerClient;
import play.libs.mailer.Email;
import play.mvc.*;
import play.data.*;

import views.html.email.*;
import models.*;

import javax.inject.Inject;
import java.io.File;

public class EmailController extends Controller {


  private final MailerClient mailer;

  @Inject
  public EmailController(MailerClient mailer) {
    this.mailer = mailer;
  }

  public Result index() {
    Form<EmailContent> emailForm = form(EmailContent.class);
    return ok(index.render(emailForm));
  }

  public Result sendMail() {
    Form<EmailContent> emailForm = form(EmailContent.class).bindFromRequest();
    final Email emailer = new Email();
      emailer.setSubject(emailForm.get().subject);
      emailer.setFrom("<csralabmailtest@gmail.com>");
      emailer.addTo("<n2i5p5y6g1c6a4x5@csra-lab.slack.com>");
      emailer.setBodyText(emailForm.get().email + "  " + emailForm.get().body);
    String id = mailer.send(emailer);
    flash("Success", "Email sent");
    Form<EmailContent> newEmailForm = form(EmailContent.class);
    return ok(index.render(newEmailForm));
  }
}
  