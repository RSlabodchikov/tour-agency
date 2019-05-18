package com.netcracker.mano.touragency.repository;

import com.netcracker.mano.touragency.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByCredentials_Login(String login);

    List<User> findAllByRole_Name(String role);

    List<User> findAll();
}
