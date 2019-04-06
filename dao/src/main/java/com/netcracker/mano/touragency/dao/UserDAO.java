package com.netcracker.mano.touragency.dao;

import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;

public interface UserDAO extends CrudDAO<User> {
     User findUserByCredentials(Credentials credentials);

     Boolean checkUserIfExist( String login);
}
