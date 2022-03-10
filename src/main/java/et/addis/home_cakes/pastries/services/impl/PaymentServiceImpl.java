package et.addis.home_cakes.pastries.services.impl;

import et.addis.home_cakes.pastries.dao.PaymentDAO;
import et.addis.home_cakes.pastries.dto.BankDto;
import et.addis.home_cakes.pastries.mapper.BankMapper;
import et.addis.home_cakes.pastries.model.Bank;
import et.addis.home_cakes.pastries.services.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Fassil on 02/02/22.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOG = LogManager.getLogger(PaymentServiceImpl.class);
    @Autowired
    PaymentDAO paymentDAO;


    @Override
    public List<BankDto> getAllBanks() {
        String pfn = "[PaymentServiceImpl::getAllBanks]";
        LOG.info(pfn + " START");
        try {
            List<Bank> banks = paymentDAO.findAllBanks();
            for(Bank b:banks) {
                LOG.info(pfn + " bank: " + b.getName());
            }
            LOG.info(pfn + " END");
            return BankMapper.INSTANCE.modelsToDtos(banks);
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }
}
