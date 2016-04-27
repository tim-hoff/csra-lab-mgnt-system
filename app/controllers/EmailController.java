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

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.play.java.RequiresAuthentication;
import org.pac4j.play.java.UserProfileController;

public class EmailController extends UserProfileController<CommonProfile> {


  private final MailerClient mailer;

  @Inject
  public EmailController(MailerClient mailer) {
        this.mailer = mailer;
  }

  @RequiresAuthentication(clientName = "CasClient")
  public Result index() {
    	if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
        Form<EmailContent> emailForm = form(EmailContent.class);
        return ok(index.render(emailForm));
  }

  public Result sendMail() {
      	if(!checkPrivilegesAdmin())
		{
			flash("error", "Insufficient Privileges");
			return redirect("/home");
		}
		
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
  