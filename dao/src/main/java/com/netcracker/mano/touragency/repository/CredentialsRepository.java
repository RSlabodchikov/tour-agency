package com.netcracker.mano.touragency.repository;

import com.netcracker.mano.touragency.entity.Credentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
    Boolean existsCredentialsByLogin(String login);
}
