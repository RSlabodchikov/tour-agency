package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.CreditCardDTO;

import java.util.List;

public interface CreditCardService {
    List<CreditCardDTO> getAllClientCards(String login);

    CreditCardDTO getById(String login, Long id);

    CreditCardDTO create(CreditCardDTO creditCard);

    void delete(Long id, String login);

    CreditCardDTO updateBalance(CreditCardDTO creditCard);

    CreditCardDTO getByGreatestBalance(String login);

    List<CreditCardDTO> getAll();
}
