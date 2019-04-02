package com.netcracker.mano.touragency.dao.Impl;

import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;

import java.util.Optional;

import static com.netcracker.mano.touragency.constants.FileNames.USER_FILENAME;

public class UserDAOImpl extends CrudDAOImpl<User> implements UserDAO {
    public UserDAOImpl() {
        super(USER_FILENAME);
    }

    @Override
    public User findUserByCredentials(Credentials credentials) {
        Optional<User> user = super.getAll()
                .stream()
                .filter(a -> credentials.equals(a.getCredentials()))
                .findFirst();
        return user.orElse(null);
    }
}
