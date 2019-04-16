package com.netcracker.mano.touragency.dao.impl.file;

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

    @Override
    public Boolean checkUserIfExist(String login) {
        Optional<User> user = super.getAll()
                .stream()
                .filter(a -> login.equals(a.getCredentials().getLogin()))
                .findFirst();
        return user.isPresent();
    }

    @Override
    public void changePassword(String login, String password) {

    }
}
