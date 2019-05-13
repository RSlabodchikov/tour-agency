package com.netcracker.mano.touragency.repository;

import com.netcracker.mano.touragency.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByCredentials_LoginAndCredentials_Password(String login, String password);
}
