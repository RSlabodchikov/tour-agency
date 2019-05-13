package com.netcracker.mano.touragency.repository;


import com.netcracker.mano.touragency.entity.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    List<CreditCard> findAllByUser_Id(Long userId);

    CreditCard findByIdAndUser_Id(Long id, Long userId);

    Boolean existsByIdAndUser_Id(Long cardId,Long userId);
}
