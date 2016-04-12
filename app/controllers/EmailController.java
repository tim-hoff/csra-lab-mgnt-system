package controllers;

import org.apache.commons.mail.EmailAttachment;
import play.Play;
import play.libs.mailer.MailerClient;
import play.libs.mailer.Email;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;

public class EmailController extends Controller {

  private final MailerClient mailer;

  @Inject
  public EmailController(MailerClient mailer) {
    this.mailer = mailer;
  }

  //filled with test datas
  public Result sendMail() {
    String cid = "1234";
    final Email email = new Email();
      email.setSubject("Simple email");
      email.setFrom("FROM <csralabmailtest@gmail.com>");
      email.addTo("TO <n2i5p5y6g1c6a4x5@csra-lab.slack.com>");
      email.setBodyText("A text message");
      email.setBodyHtml("<html><body><p>An <b>html</b> message with cid <img src=\"cid:" + cid + "\"></p></body></html>");
    String id = mailer.send(email);
    return ok("Email " + id + " sent!");
  }
}
  