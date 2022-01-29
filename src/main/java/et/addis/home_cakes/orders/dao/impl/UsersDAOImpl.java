package et.addis.home_cakes.orders.dao.impl;

import et.addis.home_cakes.orders.dao.UsersDAO;
import et.addis.home_cakes.orders.model.ConfirmationToken;
import et.addis.home_cakes.orders.model.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Fassil on 10/01/22.
 */
@Repository
public class UsersDAOImpl  implements UsersDAO {

    private static final Logger logger = LogManager.getLogger(UsersDAOImpl.class);
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext(name = "addis.homecakes.persistence")
    private EntityManager entityManager;

    public UsersDAOImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users findByEmail(String email) {
        return null;
    }

    @Override
    @Transactional
    public Optional<Users> findById(UUID id) {
        Users user = entityManager.find(Users.class, id);
        return Optional.of(user);
    }


    @Override
    @Transactional
    public boolean find(String email) {
        Query query = entityManager.createQuery("select count (u) from Users u where u.email=:email")
                .setParameter("email", email);

        Long nUsers = (Long) query.getSingleResult();
        if(nUsers==0){return false;}
        else{return true;}
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Users registerUser(Users user) {
        entityManager.persist(user);
        return entityManager.find(Users.class, user.getUserId());
    }

    @Override
    public Users updateSocialUser(String email) {
        Users u = entityManager.createQuery("select u from Users u where u.email=:email", Users.class)
                .setParameter("email", email).getSingleResult();
        entityManager.createQuery("update Users u set u.isEnabled=:isEnabled where u.email=:email").
                setParameter("isEnabled", true).setParameter("email", email).executeUpdate();
        logger.info("Social User : "+ email+ " updated");
        return u;
    }

	@Override
	public void add(Users user, ConfirmationToken confirmationToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePhone(UUID userId, String newPhone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(UUID userId, String encode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeUserPassword(String email, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(UUID userId) {
		// TODO Auto-generated method stub
		
	}

}
