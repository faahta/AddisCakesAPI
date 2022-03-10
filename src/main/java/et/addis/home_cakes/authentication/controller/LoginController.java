package et.addis.home_cakes.authentication.controller;

import et.addis.home_cakes.authentication.services.ConfirmationTokenService;
import et.addis.home_cakes.authentication.services.UserService;
import et.addis.home_cakes.integration.request.JwtRequest;
import et.addis.home_cakes.integration.response.JwtResponse;
import et.addis.home_cakes.pastries.model.ConfirmationToken;
import et.addis.home_cakes.security.CustomUserDetailsService;
import et.addis.home_cakes.security.TokenProvider;
import et.addis.home_cakes.security.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fassil on 08/01/22.
 */
@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    private static final Logger LOG = LogManager.getLogger(LoginController.class);


    @PostMapping(value = "/v1/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Authenticate user with oauth2", response = JwtResponse.class)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) throws Exception {
        // authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        LOG.info("Received login request: POST "+jwtRequest.getEmail()+", "+jwtRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        // Users user = (Users) authentication.getPrincipal();
        LOG.info("user login: "+ userDetails.getEmail() + " token: "+ jwt);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getFullName(),
                        userDetails.getEmail(), userDetails.getDateOfBirth(),
                        userDetails.getPhoneNumber(), roles) );

    }

    @GetMapping(value = "/v1/confirm-account")
    @ApiOperation(value = "Confirm user account registration.", response = ModelAndView.class)
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken){
        ConfirmationToken token = confirmationTokenService.getByConfirmationToken(confirmationToken);
        LOG.info("{confirmUserAccount} Received confirm account request: GET /confirm-account?token="+ confirmationToken);
        if(token != null) {
            UserDetails user =  userDetailsService.loadUserByUsername(token.getUser().getEmail());
            // user.setEnabled(true);
            userService.activateUser(user.getUsername());
            modelAndView.setViewName("pastryAccountVerified");
        } else {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }


}
