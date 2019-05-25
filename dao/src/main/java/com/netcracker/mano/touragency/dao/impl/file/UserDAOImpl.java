package com.netcracker.mano.touragency.dao.impl.file;

import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public class UserDAOImpl extends CrudDAOImpl<User> implements UserDAO {
    @Value("${files.user}")
    public static String filename;

    public UserDAOImpl() {
        super(filename);
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
    public Credentials findCredentialsByLogin(String login) throws EntityNotFoundException {
        Optional<User> user = super.getAll()
                .stream()
                .filter(a -> login.equals(a.getCredentials().getLogin()))
                .findFirst();
        if (!user.isPresent()) throw new EntityNotFoundException();
        else return user.get().getCredentials();
    }

    @Override
    public void changePassword(String login, String password) {

    }
}
