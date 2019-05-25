package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO register(UserDTO user);

    UserDTO update(UserDTO user);

    UserDTO findById(Long id);

    List<UserDTO> getAll();

    UserDTO findByLogin(String login);

    void block(Long id);

    void unblock(Long id);

    List<UserDTO> getAllUsersByRole(String role);

}
