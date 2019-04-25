package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.*;
import com.netcracker.mano.touragency.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Component
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User register(User user) throws RegistrationException {
        log.info("Trying to register new user :{}", user);
        if (checkUserIfExist(user.getCredentials().getLogin()))
            throw new RegistrationException();
        try {
            user.setRole(Role.CLIENT);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(user.getCredentials().getPassword().getBytes());
            String hash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
            user.getCredentials().setPassword(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException", e);
        }
        try {
            user = userDAO.add(user);
        } catch (CannotCreateEntityException e) {
            log.error("Cannot create user ", e);
            throw new RegistrationException();
        }
        return user;

    }

    private boolean checkUserIfExist(String login) {
        log.info("Check user if exist :{}", login);
        try {
            userDAO.findCredentialsByLogin(login);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public User signIn(Credentials credentials) throws AuthorizationException {
        log.info("Trying to find user by credentials :{}", credentials);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(credentials.getPassword().getBytes());
            String hash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
            credentials.setPassword(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException", e);
        }
        return userDAO.findUserByCredentials(credentials);
    }

    @Override
    public void update(User user) throws CannotUpdateEntityException {
        log.info("Trying to update user :{}", user);
        userDAO.update(user);
    }

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        log.info("Trying to get user by id :{}", id);
        return userDAO.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Trying to get all users");
        return userDAO.getAll();
    }

    @Override
    public void blockUser(Long id) throws CannotUpdateEntityException, EntityNotFoundException {
        User user = userDAO.getById(id);
        if (user != null) {
            user.setIsBlocked(true);
            update(user);
        }
    }

    @Override
    public void unblockUser(Long id) throws CannotUpdateEntityException, EntityNotFoundException {
        User user = userDAO.getById(id);
        if (user != null) {
            user.setIsBlocked(false);
            update(user);
        }
    }

    @Override
    public void changePassword(String login, String oldPassword, String newPassword) throws AuthorizationException {
        log.info("Trying to change user password");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(oldPassword.getBytes());
            oldPassword = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
            userDAO.findUserByCredentials(new Credentials(login, oldPassword));
            md.update(newPassword.getBytes());
            String hash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
            userDAO.changePassword(login, hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
