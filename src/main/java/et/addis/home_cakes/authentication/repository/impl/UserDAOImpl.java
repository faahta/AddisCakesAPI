package et.addis.home_cakes.authentication.repository.impl;

import et.addis.home_cakes.authentication.repository.UserDAO;
import et.addis.home_cakes.pastries.dao.impl.PastryDAOImpl;
import et.addis.home_cakes.pastries.model.ConfirmationToken;
import et.addis.home_cakes.pastries.model.UserRoles;
import et.addis.home_cakes.pastries.model.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Fassil on 24/02/22.
 */
@Repository
public class UserDAOImpl implements UserDAO {


    private static final Logger LOG = LogManager.getLogger(PastryDAOImpl.class);

    @PersistenceContext(name = "addis.homecakes.persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Users user, ConfirmationToken confirmationToken) {
        String pfn = "[UserDAOImpl::save]";
        LOG.info(pfn + " BEGIN");
        try {
            entityManager.persist(confirmationToken);
            for (UserRoles ur: user.getRoles()){
                UserRoles userRoles = new UserRoles(ur.getUser(), ur.getRole());
                entityManager.persist(userRoles);
            }
        } catch (Exception e){
            LOG.error(pfn + " "+ e.getMessage());
            LOG.info(pfn + " END");
            throw e;
        }

    }

    @Override
    @Transactional
    public void activateUser(String email) {
        String pfn = "[UserDAOImpl::activateUser]";
        LOG.info(pfn + " BEGIN");
        entityManager.createQuery("update Users u set u.isEnabled=:isEnabled where u.email=:email").
                setParameter("isEnabled", true).setParameter("email", email).executeUpdate();
        LOG.info("User : "+ email+ " activated");
        LOG.info(pfn + " END");
    }

    @Override
    @Transactional
    public Users findByEmail(String email) {
        String pfn = "[UserDAOImpl::findByEmail]";
        LOG.info(pfn + " BEGIN");
        Users user = null;
        try {
            user = entityManager.createQuery("select u from Users u where u.email=:email", Users.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException nre){
            LOG.info(pfn + " END");
            return null;
        }
        LOG.info(pfn + " END");
        return user;
    }

    @Override
    @Transactional
    public Optional<Users> findById(UUID id) {
        Users user = entityManager.find(Users.class, id);
        return Optional.of(user);
    }
}
