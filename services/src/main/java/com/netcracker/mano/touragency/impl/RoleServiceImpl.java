package com.netcracker.mano.touragency.impl;


import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.RoleService;
import com.netcracker.mano.touragency.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findByName(String name) throws EntityNotFoundException {
        Role role = repository.findByName(name);
        if (role == null) throw new EntityNotFoundException();
        return role;
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role findById(Long id) throws EntityNotFoundException {
        Role role = repository.findOne(id);
        if (role == null) throw new EntityNotFoundException();
        return role;
    }
}
