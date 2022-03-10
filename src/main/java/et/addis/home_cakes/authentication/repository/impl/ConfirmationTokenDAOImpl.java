package et.addis.home_cakes.authentication.repository.impl;

import et.addis.home_cakes.authentication.repository.ConfirmationTokenDAO;
import et.addis.home_cakes.authentication.services.impl.ConfirmationTokenServiceImpl;
import et.addis.home_cakes.pastries.model.ConfirmationToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created by Fassil on 25/02/22.
 */
@Repository
public class ConfirmationTokenDAOImpl implements ConfirmationTokenDAO {
    private static final Logger LOG = LogManager.getLogger(ConfirmationTokenServiceImpl.class);
    @PersistenceContext(name = "addis.homecakes.persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(ConfirmationToken confirmationToken) {
        String pfn = "[ConfirmationTokenDAOImpl::save]";
        LOG.info(pfn + " BEGIN");
        entityManager.persist(confirmationToken);
        LOG.info(pfn + " END");
    }

    @Override
    @Transactional
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        String pfn = "[ConfirmationTokenDAOImpl::findByConfirmationToken]";
        LOG.info(pfn + " BEGIN");
        ConfirmationToken confirmationToken1 = null;
        try {
            confirmationToken1 = entityManager.createQuery("select ct from ConfirmationToken ct where ct.confirmationToken=:confToken", ConfirmationToken.class).
                    setParameter("confToken", confirmationToken).getSingleResult();
        } catch (NoResultException nre){
        }
        LOG.info(pfn + " END");
        return confirmationToken1;
    }
}
