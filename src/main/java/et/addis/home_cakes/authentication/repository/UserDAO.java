package et.addis.home_cakes.authentication.repository;

import et.addis.home_cakes.pastries.model.ConfirmationToken;
import et.addis.home_cakes.pastries.model.Users;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Fassil on 24/02/22.
 */
public interface UserDAO {
    void save(Users user, ConfirmationToken confirmationToken);

    void activateUser(String username);

    Users findByEmail(String username);

    Optional<Users> findById(UUID id);

}
