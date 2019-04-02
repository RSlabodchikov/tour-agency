package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;

public interface UserService {

    void registration(User user);

    User signIn(Credentials credentials);

    void update(User user);

    User findById(Long id);

}
