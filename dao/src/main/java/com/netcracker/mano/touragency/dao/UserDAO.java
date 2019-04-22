package com.netcracker.mano.touragency.dao;

import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

public interface UserDAO extends CrudDAO<User> {
    User findUserByCredentials(Credentials credentials) throws AuthorizationException;

    Credentials findCredentialsByLogin(String login) throws EntityNotFoundException;

    void changePassword(String login, String password);

    @Override
    User add(User entity) throws CannotCreateEntityException;
}
