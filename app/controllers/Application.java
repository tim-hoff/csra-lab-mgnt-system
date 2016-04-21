package controllers;

import views.html.*;
import modules.*;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.RequiresAuthentication;
import org.pac4j.cas.profile.CasProxyProfile;
import org.w3c.dom.Document;

import models.User;

import javax.inject.Inject;
import play.mvc.*;
import play.libs.ws.*;
import java.util.concurrent.CompletionStage;
import org.pac4j.play.java.UserProfileController;
import play.mvc.Result;
import play.twirl.api.Content;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.db.jpa.Transactional;
import play.libs.XML;
import play.libs.XPath;
import play.libs.ws.WSResponse;

public class Application extends UserProfileController<CommonProfile> {
	@Inject WSClient ws;

    @RequiresAuthentication(clientName = "CasClient")
    @Transactional
    public Result casIndex() {
        final CommonProfile profile = getUserProfile();
        final String service = "https://csra-lab-mgnt-system.herokuapp.com/dashboard";
        String proxyTicket = null;
        
        if (profile instanceof CasProxyProfile) {
            final CasProxyProfile proxyProfile = (CasProxyProfile) profile;
            proxyTicket = proxyProfile.getProxyTicketFor(service);
        }

        String proxyResponse = service+"&ticket=" + proxyTicket;
        Promise<Document> documentPromise = WS.url(proxyResponse).get().map(response -> {
            return response.asXml();
        });
        
       if(User.findById(profile.getId()) == null) {
        	   return ok(casticket.render("used not found" + getUserProfile()));
       }
       
        
       return ok(test.render(profile, service, proxyTicket, profile.getId(), profile.getUsername()));
    } 


}
