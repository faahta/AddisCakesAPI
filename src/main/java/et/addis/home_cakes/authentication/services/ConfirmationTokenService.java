package et.addis.home_cakes.authentication.services;

import et.addis.home_cakes.pastries.model.ConfirmationToken;

/**
 * Created by Fassil on 25/02/22.
 */
public interface ConfirmationTokenService {
    void save(ConfirmationToken confirmationToken);
    ConfirmationToken getByConfirmationToken(String confirmationToken);
}
