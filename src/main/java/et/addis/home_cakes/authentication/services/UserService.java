package et.addis.home_cakes.authentication.services;

import et.addis.home_cakes.pastries.model.Users;

import javax.mail.MessagingException;

/**
 * Created by Fassil on 24/02/22.
 */
public interface UserService {
    void registerPastryUser(Users u) throws MessagingException; //pastry user
    void registerUser(Users u) throws MessagingException; // regular user

    void activateUser(String username);

    boolean checkIfUserExist(String email);

}
