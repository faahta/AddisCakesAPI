package et.addis.home_cakes.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.util.MultiValueMap;

/**
 * Created by Fassil on 15/09/20.
 */
public class CustomRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    private OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;
    private static final Logger logger = LogManager.getLogger(CustomRequestEntityConverter.class);
    public CustomRequestEntityConverter() {
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    }

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest req) {
        RequestEntity<?> entity = defaultConverter.convert(req);
        MultiValueMap<String, Object> params = (MultiValueMap<String, Object>) entity.getBody();
        String url = params.getFirst("redirect_uri").toString();
//        if (url.contains("facebook")) {
//            url = url.replace("http", "https");
//        }
        params.set("redirect_uri", url);
        logger.info("Callback Request Parameters: " + params.toSingleValueMap().toString());
        return new RequestEntity<>(params, entity.getHeaders(),
                entity.getMethod(), entity.getUrl());
    }

}
