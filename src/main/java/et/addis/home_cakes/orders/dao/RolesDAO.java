package et.addis.home_cakes.orders.dao;

import et.addis.home_cakes.orders.model.ERole;
import et.addis.home_cakes.orders.model.UserRoles;

/**
 * Created by Fassil on 11/01/22.
 */
public interface RolesDAO {
    UserRoles findByName(ERole name);
}
