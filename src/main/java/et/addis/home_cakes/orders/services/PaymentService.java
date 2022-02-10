package et.addis.home_cakes.orders.services;

import et.addis.home_cakes.orders.dto.BankDto;
import et.addis.home_cakes.orders.model.Bank;

import java.util.List;

/**
 * Created by Fassil on 02/02/22.
 */
public interface PaymentService {
    List<BankDto> getAllBanks();
}
