package modules;

import com.google.inject.AbstractModule;
import org.pac4j.cas.client.CasClient;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.CasOAuthWrapperClient;
import controllers.HttpActionAdapter;
import org.pac4j.play.ApplicationLogoutController;
import org.pac4j.play.CallbackController;
import org.pac4j.play.cas.logout.PlayCacheLogoutHandler;
import org.pac4j.play.store.PlayCacheStore;
import org.pac4j.cas.client.CasProxyReceptor;
import play.Configuration;
import play.Environment;

import java.io.File;

public class SecurityModule extends AbstractModule {

    private final Environment environment;
    private final Configuration configuration;

    public SecurityModule(
            Environment environment,
            Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        final String baseUrl = configuration.getString("baseUrl");

        // CAS
        final CasClient casClient = new CasClient("https://cas.latech.edu:443/cas/login");
        casClient.setLogoutHandler(new PlayCacheLogoutHandler());
        //This should grab the ticket
        final CasProxyReceptor casProxyReceptor = new CasProxyReceptor();
        casProxyReceptor.setCallbackUrl(baseUrl + "/casProxyCallback");
        casClient.setCasProxyReceptor(casProxyReceptor);
        
        final Clients clients = new Clients(baseUrl + "/callback", casClient, casProxyReceptor); 


        final Config config = new Config(clients);
        config.setHttpActionAdapter(new HttpActionAdapter());
        bind(Config.class).toInstance(config);

        // set profile timeout to 2h instead of the 1h default
        PlayCacheStore store = new PlayCacheStore();
        store.setProfileTimeout(7200);
        config.setSessionStore(store);
        
        //callback
        final CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/callback");
        bind(CallbackController.class).toInstance(callbackController);
        // logout
        final ApplicationLogoutController logoutController = new ApplicationLogoutController();
        logoutController.setDefaultUrl("https://cas.latech.edu:443/cas/logout");
        bind(ApplicationLogoutController.class).toInstance(logoutController);
    }
}