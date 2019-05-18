package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.UserDTO;
import com.netcracker.mano.touragency.entity.User;

import java.util.List;

public interface UserService {

    UserDTO register(UserDTO user);

    UserDTO update(UserDTO user);

    UserDTO findById(Long id);

    List<UserDTO> getAllUsers();

    User findByLogin(String login);

    void blockUser(Long id);

    void unblockUser(Long id);

    List<UserDTO> getAllUsersByRole(String role);

}
