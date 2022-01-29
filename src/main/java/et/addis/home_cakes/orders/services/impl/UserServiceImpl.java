package et.addis.home_cakes.orders.services.impl;

import et.addis.home_cakes.orders.dao.RolesDAO;
import et.addis.home_cakes.orders.dao.UserSocialDAO;
import et.addis.home_cakes.orders.dao.UsersDAO;
import et.addis.home_cakes.orders.mapper.UsersMapper;
import et.addis.home_cakes.orders.model.ConfirmationToken;
import et.addis.home_cakes.orders.model.ERole;
import et.addis.home_cakes.orders.model.UserRoles;
import et.addis.home_cakes.orders.model.Users;
import et.addis.home_cakes.orders.services.UserService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Fassil on 11/01/22.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UsersDAO usersDAO;
    private final PasswordEncoder encoder;
    private final RolesDAO rolesDAO;
    private final UserSocialDAO userSocialDAO;
    private final UsersMapper userMapper;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public UserServiceImpl(UsersDAO usersDAO, PasswordEncoder encoder, RolesDAO rolesDAO, UserSocialDAO userSocialDAO, UsersMapper userMapper, JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.usersDAO = usersDAO;
        this.encoder = encoder;
        this.rolesDAO = rolesDAO;
        this.userSocialDAO = userSocialDAO;
        this.userMapper = userMapper;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    @Override
    public Users getUserByEmail(String email) {
        Users user = usersDAO.findByEmail(email);
        return user;
    }
    @Override
    public Users getUserById(UUID userId) {
        Users user = usersDAO.findById(userId).get();
        return user;
    }
    @Override
    public void registerUser(Users u) throws MessagingException {
        Users user = new Users(u.getEmail(),
                encoder.encode(u.getPassword()), u.getFullName(), u.getDateOfBirth(), u.getPhoneNumber());

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        // Set<String> strRoles = u.getRoles();
        Set<UserRoles> roles = u.getRoles();
        if (roles == null) {
            UserRoles userRole = rolesDAO.findByName(ERole.ROLE_USER);
            roles.add(userRole);
        } else {
            roles.forEach(role -> {
                if ("admin".equals(role)) {
                    UserRoles adminRole = rolesDAO.findByName(ERole.ROLE_ADMIN);
                    roles.add(adminRole);
                } else if ("mod".equals(role)) {
                    UserRoles modRole = rolesDAO.findByName(ERole.ROLE_MODERATOR);
                    roles.add(modRole);
                } else {
                    UserRoles userRole = rolesDAO.findByName(ERole.ROLE_USER);
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        usersDAO.add(user, confirmationToken);

        //send welcome email message
        MimeMessage mail = javaMailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable("token",confirmationToken.getConfirmationToken());
        context.setVariable("user",user);
        String body = templateEngine.process("registrationConfirmation", context);

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);

        helper.setFrom("hexoad.bus@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Welcome to HexoBus");
        helper.setText(body, true);
        javaMailSender.send(mail);
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return usersDAO.findByEmail(email) !=null ? true : false;
    }

    @Override
    public void updateUser(String email) {
        usersDAO.updateUser(email);
    }

    @Override
    public void changePhone(UUID userId, String newPhone) {
        usersDAO.changePhone(userId, newPhone);
    }

    @Override
    public void updatePassword(UUID userId, String newPassword) {
        usersDAO.updatePassword(userId,  encoder.encode(newPassword));
    }

    @Override
    public boolean checkUserPassword(String password, Users user) {
        PasswordEncoder passencoder = new BCryptPasswordEncoder();
        String encodedPassword = user.getPassword();
        return passencoder.matches(password, encodedPassword);
    }

    @Override
    public void changeUserPassword(String email, String newPassword) {
        usersDAO.changeUserPassword(email, newPassword);
    }

    @Override
    public void deleteUserAccount(UUID userId) {
        usersDAO.deleteUser(userId);
    }


}
