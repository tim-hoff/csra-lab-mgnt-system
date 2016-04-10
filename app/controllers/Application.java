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
import javax.inject.Inject;
import play.mvc.*;
import play.libs.ws.*;
import java.util.concurrent.CompletionStage;
import org.pac4j.play.java.UserProfileController;
import play.mvc.Result;
import play.twirl.api.Content;
import play.libs.F.Function;
import play.libs.F.Promise;

public class Application extends UserProfileController<CommonProfile> {
	@Inject WSClient ws;

    @RequiresAuthentication(clientName = "CasClient")
    public Result casIndex() {
        final CommonProfile profile = getUserProfile();
        final String service = "https://csra-lab-mgnt-system.herokuapp.com/dashboard";
        String proxyTicket = null;
        //return ok(casticket.render(proxyTicket));
        
        if (profile instanceof CasProxyProfile) {
            final CasProxyProfile proxyProfile = (CasProxyProfile) profile;
            proxyTicket = proxyProfile.getProxyTicketFor(service);
        }
        
        String proxyResponse = service+"&ticket=" + proxyTicket;
        Promise<Document> documentPromise = WS.url(proxyResponse).get().map(response -> {
            return response.asXml();
        });
        
        return ok(casticket.render(proxyResponse));
    }


}
