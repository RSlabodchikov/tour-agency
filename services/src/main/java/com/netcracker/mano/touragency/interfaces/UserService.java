package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;

import java.util.List;

public interface UserService {

    User registration(User user);

    User signIn(Credentials credentials);

    void update(User user);

    User findById(Long id);

    List<User> getAllUsers();

    void blockUser(Long id);

    void unblockUser(Long id);

    void changePassword(String login, String oldPassword, String newPassword);

}
