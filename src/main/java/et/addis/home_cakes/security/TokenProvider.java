package et.addis.home_cakes.security;

import et.addis.home_cakes.authentication.repository.UserDAO;
import et.addis.home_cakes.pastries.model.Users;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Fassil on 12/09/20.
 */
@Service
public class TokenProvider {
    @Autowired
    private Environment env;
    private static final Logger LOG = LoggerFactory.getLogger(TokenProvider.class);

    private UserDAO userDAO;

    public TokenProvider() {

    }

   /* public TokenProvider(UsersDAO usersDAO, AppProperties appProperties) {
        this.usersDAO = usersDAO;
        this.appProperties = appProperties;
    }*/

    public String createToken(Authentication authentication) throws ParseException {
        String pfn = "[TokenProvider::createToken]";
        LOG.info(pfn + " START");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("User principal: "+ auth.getPrincipal().toString());
        LOG.info("auth name: "+ auth.getName());
        //UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Date now = new Date();
        DateFormat df = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Long expiryDate = df.parse(now.toString()).getTime() + Long.parseLong(this.env.getProperty("app.auth.jwtExpirationMs"));
       // Date expiryDate = new Date(now.getTime() + this.env.getProperty("app.auth.jwtExpirationMs"));
        LOG.info(pfn + " END");
        return Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiryDate))
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
        Users user = userDAO.findByEmail(claims.getSubject());
        return user;
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.env.getProperty("app.auth.tokenSecret")).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOG.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOG.error("Invalid JWT token");
        }  catch (UnsupportedJwtException ex) {
            LOG.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOG.error("JWT claims string is empty.");
        }
        return false;
    }
}
