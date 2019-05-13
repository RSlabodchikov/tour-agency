package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.exceptions.RegistrationException;

import java.util.List;

public interface UserService {

    User register(User user) throws RegistrationException;

    User signIn(Credentials credentials) throws AuthorizationException;

    User update(User user) throws CannotUpdateEntityException;

    User findById(Long id ) throws EntityNotFoundException;

    List<User> getAllUsers();

    void blockUser(Long id) throws CannotUpdateEntityException, EntityNotFoundException;

    void unblockUser(Long id) throws CannotUpdateEntityException, EntityNotFoundException;

    List<User> getAllUsersByRole(String role);

}
