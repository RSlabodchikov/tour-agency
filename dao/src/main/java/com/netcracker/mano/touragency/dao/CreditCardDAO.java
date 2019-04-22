package com.netcracker.mano.touragency.dao;

import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface CreditCardDAO extends CrudDAO<CreditCard> {
    List<CreditCard> getAllClientCards(Long clientId) throws EntityNotFoundException;
}
