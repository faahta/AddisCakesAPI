package et.addis.home_cakes.pastries.services;

import et.addis.home_cakes.pastries.dto.BankDto;

import java.util.List;

/**
 * Created by Fassil on 02/02/22.
 */
public interface PaymentService {
    List<BankDto> getAllBanks();
}
