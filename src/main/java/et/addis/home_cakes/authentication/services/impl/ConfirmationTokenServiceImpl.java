package et.addis.home_cakes.authentication.services.impl;

import et.addis.home_cakes.authentication.repository.ConfirmationTokenDAO;
import et.addis.home_cakes.authentication.services.ConfirmationTokenService;
import et.addis.home_cakes.pastries.model.ConfirmationToken;
import et.addis.home_cakes.pastries.services.impl.PastryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Fassil on 25/02/22.
 */
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private static final Logger LOG = LogManager.getLogger(ConfirmationTokenServiceImpl.class);
    @Autowired
    ConfirmationTokenDAO confirmationTokenDAO;

    @Override
    public void save(ConfirmationToken confirmationToken) {
        String pfn = "[ConfirmationTokenServiceImpl::save]";
        LOG.info(pfn + " START");
        confirmationTokenDAO.save(confirmationToken);
        LOG.info(pfn + " END");
    }
    @Override
    public ConfirmationToken getByConfirmationToken(String confirmationToken) {
        return confirmationTokenDAO.findByConfirmationToken(confirmationToken);

    }
}
