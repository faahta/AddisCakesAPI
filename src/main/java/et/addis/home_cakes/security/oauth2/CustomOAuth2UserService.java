package et.addis.home_cakes.security.oauth2;

import et.addis.home_cakes.orders.dao.UsersDAO;
import et.addis.home_cakes.orders.model.Users;
import et.addis.home_cakes.security.AuthProvider;
import et.addis.home_cakes.security.UserPrincipal;
import et.addis.home_cakes.security.oauth2.user.OAuth2UserInfo;
import et.addis.home_cakes.security.oauth2.user.OAuth2UserInfoFactory;
import et.addis.home_cakes.util.exception.OAuth2AuthenticationProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Fassil on 12/09/20.
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    private UsersDAO usersDAO;

    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(CustomOAuth2UserService.class);
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);

    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        logger.info("Processing User: "+ oAuth2UserInfo.getEmail());
        boolean userExists = usersDAO.find(oAuth2UserInfo.getEmail());
        Users user;
        if(userExists){
//            user = userRepository.findByEmail(oAuth2UserInfo.getEmail());
//            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
//                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
//                        user.getProvider() + " account. Please use your " + user.getProvider() +
//                        " account to login.");
//            }
            user = updateExistingUser(oAuth2UserInfo);
        } else{
            logger.info("Registering new social user: ");
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }


        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private Users registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Users user = new Users();
        logger.info("sub; "+ oAuth2UserInfo.getAttributes().toString());
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setFullName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setDateOfBirth(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        user.setPhoneNumber(oAuth2UserInfo.getId());
        user.setPassword(passwordEncoder.encode(oAuth2UserInfo.getName()));
        logger.info("User :"+ user.getEmail() +  "registered");
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return usersDAO.registerUser(user);
    }

    private Users updateExistingUser(OAuth2UserInfo oAuth2UserInfo) {
       // existingUser.setFullName(oAuth2UserInfo.getName());
       // existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return usersDAO.updateSocialUser(oAuth2UserInfo.getEmail());
    }
}
