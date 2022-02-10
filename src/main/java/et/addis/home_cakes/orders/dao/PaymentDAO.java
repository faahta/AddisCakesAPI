package et.addis.home_cakes.orders.dao;

import et.addis.home_cakes.orders.model.Bank;

import java.util.List;

/**
 * Created by Fassil on 02/02/22.
 */
public interface PaymentDAO {
    List<Bank> findAllBanks();
}
