package et.addis.home_cakes.orders.dao;

import et.addis.home_cakes.orders.model.ConfirmationToken;

/**
 * Created by Fassil on 11/01/22.
 */
public interface ConfirmationTokenDAO {
    void save(ConfirmationToken confirmationToken);
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
