package et.addis.home_cakes.orders.dao;

import et.addis.home_cakes.orders.model.ConfirmationToken;
import et.addis.home_cakes.orders.model.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Fassil on 10/01/22.
 */
public interface UsersDAO {
    Users findByEmail(String email);
    Optional<Users> findById(UUID id);
    boolean find(String email);
    Users registerUser(Users user);
    Users updateSocialUser(String email);

    void add(Users user, ConfirmationToken confirmationToken);

    void updateUser(String email);

    void changePhone(UUID userId, String newPhone);

    void updatePassword(UUID userId, String encode);

    void changeUserPassword(String email, String newPassword);

    void deleteUser(UUID userId);
}
