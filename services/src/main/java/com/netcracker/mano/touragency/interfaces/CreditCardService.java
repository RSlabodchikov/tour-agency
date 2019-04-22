package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface CreditCardService {
    List<CreditCard> getAllClientCards(Long userId) throws EntityNotFoundException;

    CreditCard getById(Long clientId, Long cardId) throws EntityNotFoundException;

    CreditCard create(Double balance, Long id) throws CannotCreateEntityException;

    void delete(Long cardId, Long clientId) throws EntityNotFoundException;

    CreditCard updateBalance(Long cardId, Double balance, Long userId) throws CannotUpdateEntityException, EntityNotFoundException;

    CreditCard getByGreatestBalance(Long userId) throws EntityNotFoundException;


}
