package et.addis.home_cakes.security.oauth2.user;


import et.addis.home_cakes.security.AuthProvider;
import et.addis.home_cakes.util.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

/**
 * Created by Fassil on 12/09/20.
 */
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
