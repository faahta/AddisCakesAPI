package et.addis.home_cakes.security;

import et.addis.home_cakes.orders.dao.UsersDAO;
import et.addis.home_cakes.orders.model.Users;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Fassil on 12/09/20.
 */
@Service
public class TokenProvider {
    @Autowired
    private Environment env;
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private UsersDAO usersDAO;

    public TokenProvider() {

    }

   /* public TokenProvider(UsersDAO usersDAO, AppProperties appProperties) {
        this.usersDAO = usersDAO;
        this.appProperties = appProperties;
    }*/

    public String createToken(Authentication authentication) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("User principal: "+ auth.getPrincipal().toString());
        logger.info("auth name: "+ auth.getName());
        //UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.env.getProperty("app.auth.jwtExpirationMs"));

        return Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, this.env.getProperty("app.auth.tokenSecret"))
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.env.getProperty("app.auth.tokenSecret"))
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString (claims.getSubject());
    }

    public Users getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.env.getProperty("app.auth.tokenSecret"))
                .parseClaimsJws(token)
                .getBody();
        Users user = usersDAO.findByEmail(claims.getSubject());
        return user;
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.env.getProperty("app.auth.tokenSecret")).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        }  catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
