package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.CreditCardDTO;

import java.util.List;

public interface CreditCardService {
    List<CreditCardDTO> getAllClientCards();

    CreditCardDTO getById(Long id);

    CreditCardDTO create(CreditCardDTO creditCard);

    void delete(Long id);

    CreditCardDTO updateBalance(CreditCardDTO creditCard);

    CreditCardDTO getByGreatestBalance();

    List<CreditCardDTO> getAll();
}
