package com.netcracker.mano.touragency.repository;

import com.netcracker.mano.touragency.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
    List<Role> findAll();
}
