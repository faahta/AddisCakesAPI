package et.addis.home_cakes.orders.dao.impl;

import et.addis.home_cakes.orders.dao.ConfirmationTokenDAO;
import et.addis.home_cakes.orders.model.ConfirmationToken;
import org.springframework.stereotype.Repository;

/**
 * Created by Fassil on 11/01/22.
 */
@Repository
public class ConfirmationTokenDAOImpl implements ConfirmationTokenDAO {
    @Override
    public void save(ConfirmationToken confirmationToken) {

    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return null;
    }
}
