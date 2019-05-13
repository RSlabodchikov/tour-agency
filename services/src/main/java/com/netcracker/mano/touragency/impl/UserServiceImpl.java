package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.exceptions.RegistrationException;
import com.netcracker.mano.touragency.interfaces.RoleService;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.repository.CredentialsRepository;
import com.netcracker.mano.touragency.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private CredentialsRepository credentialsRepository;

    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, CredentialsRepository credentialsRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.roleService = roleService;
    }

    @Override
    public User register(User user) throws RegistrationException {
        log.info("Trying to register new user :{}", user);
        if (checkUserIfExist(user.getCredentials().getLogin()))
            throw new RegistrationException();
        try {
            user.setIsBlocked(false);
            try {
                user.setRole(roleService.findByName("client"));
            } catch (EntityNotFoundException e) {
                throw new RegistrationException();
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(user.getCredentials().getPassword().getBytes());
            String hash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
            user.getCredentials().setPassword(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException", e);
        }
        return userRepository.save(user);
    }

    private boolean checkUserIfExist(String login) {
        log.info("Check user if exist :{}", login);
        return credentialsRepository.existsCredentialsByLogin(login);
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
        User user = userRepository.findByCredentials_LoginAndCredentials_Password(
                credentials.getLogin(), credentials.getPassword());
        if (user == null) throw new AuthorizationException();
        return user;
    }

    @Override
    public User update(User user) throws CannotUpdateEntityException {
        log.info("Trying to update user :{}", user);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(user.getCredentials().getPassword().getBytes());
            user.getCredentials().setPassword(DatatypeConverter.printHexBinary(md.digest()).toUpperCase());
        } catch (NoSuchAlgorithmException e) {
            throw new CannotUpdateEntityException();
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        log.info("Trying to get user by id :{}", id);
        User user = userRepository.findOne(id);
        if (user == null) throw new EntityNotFoundException();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Trying to get all users");
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public void blockUser(Long id) throws CannotUpdateEntityException, EntityNotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) throw new EntityNotFoundException();
        if (user.getIsBlocked()) throw new CannotUpdateEntityException();
        user.setIsBlocked(true);
        update(user);

    }

    @Override
    public void unblockUser(Long id) throws CannotUpdateEntityException, EntityNotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) throw new EntityNotFoundException();
        if (!user.getIsBlocked()) throw new CannotUpdateEntityException();
        user.setIsBlocked(false);
        update(user);

    }

    @Override
    public List<User> getAllUsersByRole(String role) {
        return userRepository.findAllByRole_Name(role);
    }
}
