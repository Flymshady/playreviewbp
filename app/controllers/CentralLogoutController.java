package controllers;

import com.typesafe.config.Config;
import org.pac4j.play.LogoutController;

import javax.inject.Inject;

public class CentralLogoutController extends LogoutController {

    @Inject
    public CentralLogoutController(Config config) {
        String baseUrl = config.getString("baseUrl");
        setDefaultUrl(baseUrl + "/");
        setLocalLogout(true);
        setCentralLogout(true);
        setLogoutUrlPattern(baseUrl + "/.*");
    }
}