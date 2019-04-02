package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.CreditCard;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<CreditCard> getAllClientCards(Long userId);

    Optional<CreditCard> getCardById(Long clientId, Long cardId);

    void createCard(Double balance, Long id);

    void deleteCard(Long cardId);

}
