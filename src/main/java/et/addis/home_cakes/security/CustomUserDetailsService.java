package et.addis.home_cakes.security;

import et.addis.home_cakes.orders.dao.UsersDAO;
import et.addis.home_cakes.orders.model.Users;
import et.addis.home_cakes.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by Fassil on 12/09/20.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsersDAO usersDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Users user = usersDAO.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(UUID id) {
        Users user = usersDAO.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
