package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {
    List<CreditCard> getAllClientCards(Long userId);

    Optional<CreditCard> getById(Long clientId, Long cardId) throws EntityNotFoundException;

    CreditCard create(Double balance, Long id) throws CannotCreateEntityException;

    void delete(Long cardId, Long clientId);

    CreditCard updateBalance(Long cardId, Double balance, Long userId) throws CannotUpdateEntityException;

    Optional<CreditCard> getByGreatestBalance(Long userId);
}
