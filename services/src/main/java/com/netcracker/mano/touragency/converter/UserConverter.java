package com.netcracker.mano.touragency.converter;

import com.netcracker.mano.touragency.dto.UserDTO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convertToEntity(UserDTO userDTO) {
        Credentials credentials = Credentials.builder()
                //.id(userDTO.getCredentialsId())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .build();

        Role role = Role.builder()
                .name(userDTO.getRole())
                .id(userDTO.getRoleId())
                .build();

        return User.builder()
                .isBlocked(userDTO.getIsBlocked())
                .id(userDTO.getId())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .role(role)
                .credentials(credentials)
                .build();
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setIsBlocked(user.getIsBlocked());

        userDTO.setRole(user.getRole().getName());
        userDTO.setRoleId(user.getRole().getId());

        userDTO.setCredentialsId(user.getCredentials().getId());
        userDTO.setLogin(user.getCredentials().getLogin());
        userDTO.setPassword(user.getCredentials().getPassword());

        return userDTO;
    }
}
