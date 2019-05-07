package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.CreditCard;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {
    List<CreditCard> getAllClientCards(Long userId);

    Optional<CreditCard> getById(Long clientId, Long cardId);

    void create(Double balance, Long id);

    void delete(Long cardId, Long clientId);

    CreditCard updateBalance(Long cardId, Double balance, Long userId);

    Optional<CreditCard> getByGreatestBalance(Long userId);
}
