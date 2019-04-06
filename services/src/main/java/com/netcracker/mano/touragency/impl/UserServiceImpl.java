package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.Impl.UserDAOImpl;
import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.interfaces.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public User registration(User user) {
        if (userDAO.checkUserIfExist(user.getCredentials().getLogin())) return null;
        return userDAO.add(user);

    }

    @Override
    public User signIn(Credentials credentials) {
        return userDAO.findUserByCredentials(credentials);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User findById(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    @Override
    public void blockUser(Long id) {
        User user = userDAO.getById(id);
        if (user != null) {
            user.setIsBlocked(true);
            update(user);
        }
    }

    @Override
    public void unblockUser(Long id) {
        User user = userDAO.getById(id);
        if (user != null) {
            user.setIsBlocked(false);
            update(user);
        }
    }
}
