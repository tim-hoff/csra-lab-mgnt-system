
import akka.actor.ActorRef;
import akka.actor.Props;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;

import models.*;
import java.util.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.*;
import play.db.jpa.JPA;
import javax.persistence.*;
import org.apache.commons.mail.EmailAttachment;
import play.Play;
import play.libs.mailer.MailerPlugin;
import play.libs.mailer.Email;

public class Global extends GlobalSettings {
    
    List<User> data;

	@Override
    public void onStart(Application application) {

    	Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.SECONDS),
                Duration.create(24, TimeUnit.HOURS),
                new Runnable() {
                    @Override
                    public void run() {
                    	Logger.info("Running Overdue Item Mailouts");

                        JPA.withTransaction (() -> { 
                            data = JPA.em()
                            .createQuery("Select u.user_id From Inventory i, User u Where Now() > i.return_date AND i.return_date is not null AND i.rented_by = u.user_id AND (DATEDIFF(current_timestamp, i.last_notified) > -1) AND (DATEDIFF(current_timestamp, i.last_notified) % 3 = 0)")
                            .getResultList();
                        });

						for (int i = 0; i < data.size(); i++) {
					        final Email emailer = new Email();
					        emailer.setSubject("test");
					        emailer.setFrom("<csralabmailtest@gmail.com>");
					        emailer.addTo("<n2i5p5y6g1c6a4x5@csra-lab.slack.com>");
					        emailer.setBodyText("<" + data.get(i) + "@latech.edu>" + "\n\n  TEST");
					        MailerPlugin.send(emailer);
    					}
                    }
                },
                Akka.system().dispatcher()
        );
    }
}