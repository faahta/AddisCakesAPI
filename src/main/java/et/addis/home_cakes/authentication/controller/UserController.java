package et.addis.home_cakes.authentication.controller;

import et.addis.home_cakes.authentication.services.UserService;
import et.addis.home_cakes.pastries.model.Users;
import et.addis.home_cakes.util.exception.UserAlreadyExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * Created by Fassil on 06/03/22.
 */
@RestController
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping(value = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody Users user) throws UserAlreadyExistsException, MessagingException, MessagingException {
        LOG.info("{registerUser} - Received user registration request: POST /v1/user "+user.getEmail());
        // Let's check if user already registered with us
        if(userService.checkIfUserExist (user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists for this email");
        }
        userService.registerUser(user);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
