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

import org.pac4j.play.java.UserProfileController;
import play.mvc.Result;
import play.twirl.api.Content;

public class Application extends UserProfileController<CommonProfile> {
    

    @RequiresAuthentication(clientName = "CasClient")
    public Result casIndex() {
        final CommonProfile profile = getUserProfile();
        //final String service = "http://localhost:8080/proxiedService";
        final String service = "https://csra-lab-mgnt-system.herokuapp.com/dashboard";
        String proxyTicket = null;
        if (profile instanceof CasProxyProfile) {
            final CasProxyProfile proxyProfile = (CasProxyProfile) profile;
            proxyTicket = proxyProfile.getProxyTicketFor(service);
        }
        //return ok(index.render());
        return ok(test.render(profile, service, proxyTicket));
    }


}
