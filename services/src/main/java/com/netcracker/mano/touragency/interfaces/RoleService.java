package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO findByName(String name);

    List<RoleDTO> findAll();

    RoleDTO findById(Long id);
}
