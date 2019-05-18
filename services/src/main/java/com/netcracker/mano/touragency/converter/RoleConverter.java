package com.netcracker.mano.touragency.converter;


import com.netcracker.mano.touragency.dto.RoleDTO;
import com.netcracker.mano.touragency.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {
    public Role convertToEntity(RoleDTO roleDTO) {
        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .build();
    }

    public RoleDTO convertToDTO(Role role) {
        return new RoleDTO(role.getId(), role.getName());
    }
}
