package et.addis.home_cakes.orders.services;

import et.addis.home_cakes.orders.model.Users;

import javax.mail.MessagingException;
import java.util.UUID;

/**
 * Created by Fassil on 11/01/22.
 */
public interface UserService {
    Users getUserById(UUID userId);
    Users getUserByEmail(String email);
    void registerUser(Users user) throws MessagingException;
    boolean checkIfUserExist(String email);
    void updateUser(String email);

    void changePhone(UUID userId, String newPhone);

    void updatePassword(UUID userId, String newPassword);

    boolean checkUserPassword(String password, Users user);

    void changeUserPassword(String email, String password);

    void deleteUserAccount(UUID userId);

   /* List<UsersDto> getAllUsers();*/
}
