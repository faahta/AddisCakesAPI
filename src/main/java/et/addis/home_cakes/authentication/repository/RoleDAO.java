package et.addis.home_cakes.authentication.repository;

import et.addis.home_cakes.pastries.model.ERole;
import et.addis.home_cakes.pastries.model.UserRoles;

/**
 * Created by Fassil on 24/02/22.
 */
public interface RoleDAO {
    UserRoles findByName(ERole name);
}
