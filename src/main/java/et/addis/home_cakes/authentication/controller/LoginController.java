package et.addis.home_cakes.authentication.controller;

import et.addis.home_cakes.integration.request.JwtRequest;
import et.addis.home_cakes.integration.response.JwtResponse;
import et.addis.home_cakes.orders.dao.ConfirmationTokenDAO;
import et.addis.home_cakes.orders.services.UserService;
import et.addis.home_cakes.security.CustomUserDetailsService;
import et.addis.home_cakes.security.TokenProvider;
import et.addis.home_cakes.security.UserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fassil on 08/01/22.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    final AuthenticationManager authenticationManager;

    final TemplateEngine templateEngine;

    final TokenProvider tokenProvider;
    final UserService userService;

    final CustomUserDetailsService userDetailsService;
    final ConfirmationTokenDAO confirmationTokenDAO;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public LoginController(AuthenticationManager authenticationManager,
                           TemplateEngine templateEngine, TokenProvider tokenProvider,
                           UserService userService, CustomUserDetailsService userDetailsService,
                           ConfirmationTokenDAO confirmationTokenDAO) {
        this.authenticationManager = authenticationManager;
        //this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.confirmationTokenDAO = confirmationTokenDAO;
    }

    @PostMapping(value = "/v1/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws Exception {
        // authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        logger.info("Received login request: POST "+jwtRequest.getEmail()+", "+jwtRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        // Users user = (Users) authentication.getPrincipal();
        logger.info("user login: "+ userDetails.getEmail() + " token: "+ jwt);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getFullName(),
                        userDetails.getEmail(), userDetails.getDateOfBirth(),
                        userDetails.getPhoneNumber(), roles) );

    }

}
