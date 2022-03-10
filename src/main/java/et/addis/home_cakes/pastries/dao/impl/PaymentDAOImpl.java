package et.addis.home_cakes.pastries.dao.impl;

import et.addis.home_cakes.pastries.dao.PaymentDAO;
import et.addis.home_cakes.pastries.model.Bank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Fassil on 02/02/22.
 */
@Repository
public class PaymentDAOImpl implements PaymentDAO {

    private static final Logger LOG = LogManager.getLogger(PaymentDAOImpl.class);
    @PersistenceContext(name = "addis.homecakes.persistence")
    private EntityManager entityManager;


    @Override
    public List<Bank> findAllBanks() {
        String pfn = "[PaymentDAOImpl::findAllBanks]";
        LOG.info(pfn + " BEGIN");
        try {
            Query query = entityManager.createQuery("select b from Bank b", Bank.class);
            List<Bank> banks = query.getResultList();
            LOG.info(pfn + " END");
            return banks;
        } catch (Exception e){
            LOG.error(pfn + " "+ e.getMessage());
            LOG.info(pfn + " END");
            throw e;
        }
    }
}
