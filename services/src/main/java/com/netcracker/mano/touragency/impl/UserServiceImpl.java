package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.Impl.UserDAOImpl;
import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.interfaces.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void registration(User user) {
        userDAO.add(user);

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
}
