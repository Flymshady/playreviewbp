
package modules;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import controllers.SecureHttpActionAdapter;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.pac4j.play.CallbackController;
import org.pac4j.play.LogoutController;
import org.pac4j.play.deadbolt2.Pac4jRoleHandler;
import org.pac4j.play.store.PlayCacheSessionStore;
import org.pac4j.play.store.PlaySessionStore;
import play.Environment;
import play.cache.SyncCacheApi;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class SecurityModule extends AbstractModule {
    private final com.typesafe.config.Config configuration;
    private static class MyPac4jRoleHandler implements Pac4jRoleHandler {}
    private String baseUrl;

    public SecurityModule(final Environment environment, final com.typesafe.config.Config configuration) {
        this.configuration = configuration;
        this.baseUrl=configuration.getString("baseUrl");
    }
    @Override
    protected void configure() {

        bind(Pac4jRoleHandler.class).to(MyPac4jRoleHandler.class);
        final PlayCacheSessionStore playCacheSessionStore = new PlayCacheSessionStore(getProvider(SyncCacheApi.class));
        bind(PlaySessionStore.class).to(PlayCacheSessionStore.class);

        //callback
        final CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/");
        callbackController.setMultiProfile(true);
        callbackController.setRenewSession(true);
        bind(CallbackController.class).toInstance(callbackController);

        //logout
        final LogoutController logoutController = new LogoutController();
        logoutController.setDefaultUrl("/");
        bind(LogoutController.class).toInstance(logoutController);
    }


    @Provides
    protected OidcClient provideOidcClient() {
        final OidcConfiguration oidcConfiguration = new OidcConfiguration();
        oidcConfiguration.setClientId(configuration.getString("oidc.clientId"));
        oidcConfiguration.setSecret(configuration.getString("oidc.clientSecret"));
        oidcConfiguration.setDiscoveryURI(configuration.getString("oidc.discoveryUri"));
        final OidcClient oidcClient = new OidcClient(oidcConfiguration);
      //  oidcClient.addAuthorizationGenerator((ctx, profile) -> { profile.addRole("ROLE_ADMIN"); return profile; });
        oidcClient.addAuthorizationGenerator((ctx, profile) -> {
            if (profile.getAttribute("groups") != null) {
                List<String> groups = (List) profile.getAttribute("groups");
                Set<String> filteredGroups = groups.stream()
                        .filter(group -> group.startsWith("ROLE_"))
                        .collect(Collectors.toSet());
                profile.addRoles(filteredGroups);
            }
            return profile;
        });
        return oidcClient;
    }

    @Provides
    protected Config provideConfig(OidcClient oidcClient) {

        final Clients clients = new Clients(baseUrl + "/callback", oidcClient);
        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));
        config.addAuthorizer("user", new RequireAnyRoleAuthorizer<>("ROLE_USER"));
        config.setHttpActionAdapter(new SecureHttpActionAdapter());
        return config;
    }
/*

        bind(PlaySessionStore.class).to(PlayCacheSessionStore.class);
        final OidcConfiguration oidcConfiguration = new OidcConfiguration();
        oidcConfiguration.setDiscoveryURI(configuration.getString("oidc.discoveryUri"));
        oidcConfiguration.setClientId(configuration.getString("oidc.clientId"));
        oidcConfiguration.setSecret(configuration.getString("oidc.clientSecret"));
        final OidcClient oidcClient = new OidcClient(oidcConfiguration);
        oidcClient.addAuthorizationGenerator((ctx, profile) -> {
            profile.addRole("ROLE_ADMIN");
            return profile;
        });
        final String baseUrl = configuration.getString("baseUrl");
        final Clients clients = new Clients(baseUrl + "/callback",  oidcClient);
        final org.pac4j.core.config.Config config = new org.pac4j.core.config.Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));
        config.setHttpActionAdapter(new SecureHttpActionAdapter());
        bind(org.pac4j.core.config.Config.class).toInstance(config);

        final CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/");
        callbackController.setMultiProfile(true);
        bind(CallbackController.class).toInstance(callbackController);

        final LogoutController logoutController = new LogoutController();
        logoutController.setDefaultUrl("/?defaulturlafterlogout");
        bind(LogoutController.class).toInstance(logoutController);
*/
}