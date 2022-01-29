package et.addis.home_cakes.util.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Fassil on 14/09/20.
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
