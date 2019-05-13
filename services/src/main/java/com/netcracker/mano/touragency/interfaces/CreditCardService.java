package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface CreditCardService {
    List<CreditCard> getAllClientCards(Long userId) throws EntityNotFoundException;

    CreditCard getById(Long clientId, Long cardId) throws EntityNotFoundException;

    CreditCard create(CreditCard creditCard) throws CannotCreateEntityException;

    void delete(Long cardId, Long clientId) throws EntityNotFoundException;

    CreditCard updateBalance(CreditCard creditCard) throws CannotUpdateEntityException, EntityNotFoundException;

    CreditCard getByGreatestBalance(Long userId) throws EntityNotFoundException;

    List<CreditCard> getAll();
}
