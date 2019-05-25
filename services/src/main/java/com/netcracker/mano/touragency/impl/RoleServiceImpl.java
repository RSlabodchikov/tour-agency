package com.netcracker.mano.touragency.impl;


import com.netcracker.mano.touragency.converter.RoleConverter;
import com.netcracker.mano.touragency.dto.RoleDTO;
import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.RoleService;
import com.netcracker.mano.touragency.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository repository;

    private RoleConverter converter;

    @Autowired
    public RoleServiceImpl(RoleRepository repository, RoleConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public RoleDTO findByName(String name) {
        Role role = repository.findByName(name);
        if (role == null) throw new EntityNotFoundException("Cannot find role with this name");
        return converter.convertToDTO(role);
    }

    @Override
    public List<RoleDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role = repository.findOne(id);
        if (role == null) throw new EntityNotFoundException("Cannot find role with this name");
        return converter.convertToDTO(role);
    }


}
