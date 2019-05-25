package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.RoleConverter;
import com.netcracker.mano.touragency.dto.RoleDTO;
import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl service;

    @Mock
    private RoleRepository repository;

    @Mock
    private RoleConverter converter;

    private Role role;

    private RoleDTO roleDTO;

    @Before
    public void init() {
        role = new Role(1L, "admin");
        roleDTO = new RoleDTO(1L, "admin");
        initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindById() {
        when(repository.findOne(anyLong())).thenReturn(null);
        service.findById(1L);
    }

    @Test
    public void findById() {
        when(repository.findOne(anyLong())).thenReturn(role);
        when(converter.convertToDTO(any())).thenReturn(roleDTO);
        assertThat(service.findById(1L), is(roleDTO));
    }

    @Test
    public void findAll() {
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        when(repository.findAll()).thenReturn(roles);
        when(converter.convertToDTO(any())).thenReturn(roleDTO);
        assertThat(service.findAll().size(), is(1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindByName() {
        when(repository.findByName(anyString())).thenReturn(null);
        service.findByName("client");
    }

    @Test
    public void findByName() {
        when(repository.findByName(anyString())).thenReturn(role);
        when(converter.convertToDTO(role)).thenReturn(roleDTO);
        assertThat(service.findByName("admin"), is(roleDTO));
    }


}