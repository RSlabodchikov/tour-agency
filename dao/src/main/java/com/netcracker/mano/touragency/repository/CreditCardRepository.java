package com.netcracker.mano.touragency.repository;


import com.netcracker.mano.touragency.entity.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    List<CreditCard> findAllByUser_Credentials_Login(String login);

    CreditCard findByIdAndUser_Credentials_Login(Long id, String login);

    Boolean existsByIdAndUser_Credentials_Login(Long cardId, String login);

    List<CreditCard> findAll();
}
