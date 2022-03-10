package et.addis.home_cakes.authentication.repository.impl;

import et.addis.home_cakes.authentication.repository.RoleDAO;
import et.addis.home_cakes.pastries.dao.impl.PastryDAOImpl;
import et.addis.home_cakes.pastries.model.ERole;
import et.addis.home_cakes.pastries.model.UserRoles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Fassil on 24/02/22.
 */
@Repository
public class RoleDAOImpl implements RoleDAO {
    private static final Logger LOG = LogManager.getLogger(PastryDAOImpl.class);

    @PersistenceContext(name = "addis.homecakes.persistence")
    private EntityManager entityManager;
    @Override
    public UserRoles findByName(ERole name) {
        String pfn = "[RoleDAOImpl::findByName]";
        LOG.info(pfn + " BEGIN");
        try {
            Query query =  entityManager.createQuery("select r from UserRoles r where r.role.name=:name", UserRoles.class)
                    .setParameter("name", name);
            LOG.info(pfn + " END");
            return (UserRoles) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info(pfn + " Error: " + e.getMessage());
            return null;
           // throw e;
        }

    }
}
