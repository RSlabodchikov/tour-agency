package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface RoleService {
    Role findByName(String name) throws EntityNotFoundException;

    List<Role> findAll();

    Role findById(Long id) throws EntityNotFoundException;
}
