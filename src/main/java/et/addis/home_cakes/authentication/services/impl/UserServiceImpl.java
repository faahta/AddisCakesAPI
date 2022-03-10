package et.addis.home_cakes.authentication.services.impl;

import et.addis.home_cakes.authentication.repository.RoleDAO;
import et.addis.home_cakes.authentication.repository.UserDAO;
import et.addis.home_cakes.authentication.services.UserService;
import et.addis.home_cakes.pastries.model.ConfirmationToken;
import et.addis.home_cakes.pastries.model.ERole;
import et.addis.home_cakes.pastries.model.UserRoles;
import et.addis.home_cakes.pastries.model.Users;
import et.addis.home_cakes.pastries.services.impl.PastryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Set;

/**
 * Created by Fassil on 24/02/22.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void registerPastryUser(Users u) throws MessagingException {
        String pfn = "[UserServiceImpl::registerPastryUser]";
        LOG.info(pfn + " START");
        Users user = new Users(u.getEmail(), encoder.encode(u.getPassword()), u.getFullName());
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        Set<UserRoles> roles = u.getRoles();
        if (roles == null) {
            UserRoles userRole = roleDAO.findByName(ERole.ROLE_PASTRY);
            roles.add(userRole);
        } else {
            LOG.info(pfn + " User roles found...");
            roles.forEach(role -> {
                UserRoles userRole = roleDAO.findByName(ERole.ROLE_PASTRY);
                roles.add(userRole);
            });
        }
        user.setRoles(roles);
        userDAO.save(user, confirmationToken);
        // SEND WELCOME EMAIL
        MimeMessage mail = javaMailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable("token",confirmationToken.getConfirmationToken());
        context.setVariable("user",user);
        context.setVariable("email",u.getEmail());
        context.setVariable("pass", u.getPassword());
        String body = templateEngine.process("pastryRegistrationConfirmation", context);

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setFrom("addis.cakes.master@gmail.com");
        helper.setTo(u.getEmail());
        helper.setSubject("Welcome to Addis cakes");
        helper.setText(body, true);
        javaMailSender.send(mail);
        LOG.info(pfn + " END");
    }

    // FOR REGULAR USERS
    @Override
    public void registerUser(Users u) throws MessagingException {
        String pfn = "[UserServiceImpl::registerUser]";
        LOG.info(pfn + " START");
        Users user = new Users(u.getEmail(), encoder.encode(u.getPassword()), u.getFullName());
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        Set<UserRoles> roles = u.getRoles();
        if (roles == null) {
            UserRoles userRole = roleDAO.findByName(ERole.ROLE_USER);
            roles.add(userRole);
        } else {
            LOG.info(pfn + " User roles found...");
            roles.forEach(role -> {
                UserRoles userRole = roleDAO.findByName(ERole.ROLE_USER);
                roles.add(userRole);
            });
        }
        user.setRoles(roles);
        userDAO.save(user, confirmationToken);
        // SEND WELCOME EMAIL
        MimeMessage mail = javaMailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable("token",confirmationToken.getConfirmationToken());
        context.setVariable("user",user);
        context.setVariable("email",u.getEmail());
        String body = templateEngine.process("userRegistrationConfirmation", context);

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setFrom("addis.cakes.master@gmail.com");
        helper.setTo(u.getEmail());
        helper.setSubject("Welcome to Addis cakes");
        helper.setText(body, true);
        javaMailSender.send(mail);
        LOG.info(pfn + " END");
    }

    @Override
    public void activateUser(String email) {
        userDAO.activateUser(email);
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return false;
    }
}
