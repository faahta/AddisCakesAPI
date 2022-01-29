package et.addis.home_cakes.orders.dao.impl;

import et.addis.home_cakes.orders.dao.RolesDAO;
import et.addis.home_cakes.orders.model.ERole;
import et.addis.home_cakes.orders.model.UserRoles;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Fassil on 11/01/22.
 */
@Repository
public class RolesDAOImpl implements RolesDAO {

    @PersistenceContext(name = "addis.homecakes.persistence")
    private EntityManager entityManager;

    @Override
    public UserRoles findByName(ERole name) {
        Query query =  entityManager.createQuery("select r from UserRoles r where r.role.name=:name", UserRoles.class)
                .setParameter("name", name);
        return (UserRoles) query.getSingleResult();
    }
}
