package et.addis.home_cakes.authentication.repository;

import et.addis.home_cakes.pastries.model.ConfirmationToken;

/**
 * Created by Fassil on 25/02/22.
 */
public interface ConfirmationTokenDAO {
    void save(ConfirmationToken confirmationToken);
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
