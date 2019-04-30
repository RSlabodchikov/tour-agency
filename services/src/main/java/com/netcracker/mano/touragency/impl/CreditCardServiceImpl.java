package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {
    private Random random = new Random();


    private CreditCardDAO creditCardDAO;

    public CreditCardServiceImpl(CreditCardDAO creditCardDAO) {
        this.creditCardDAO = creditCardDAO;
    }

    @Override
    public List<CreditCard> getAllClientCards(Long userId) throws EntityNotFoundException {
        log.debug("Trying to get all client cards :{}", userId);
        return creditCardDAO.getAllClientCards(userId);

    }

    @Override
    public CreditCard getById(Long clientId, Long cardId) throws EntityNotFoundException {
        log.debug("Trying to get card by id :{}", cardId);
        Optional<CreditCard> card = getAllClientCards(clientId)
                .stream()
                .filter(a -> a.getId() == cardId)
                .findFirst();
        if (card.isPresent()) {
            return card.get();
        } else throw new EntityNotFoundException();
    }

    @Override
    public CreditCard create(Double balance, Long id) throws CannotCreateEntityException {
        log.debug("Trying to create credit card with balance :{}", balance);
        if (balance < 0) throw new CannotCreateEntityException();
        CreditCard card = new CreditCard();
        card.setBalance(balance);
        card.setUserId(id);
        card.setNumber(BigInteger.valueOf((Math.abs(random.nextLong()))));
        return creditCardDAO.add(card);
    }

    @Override
    public void delete(Long cardId, Long clientId) throws EntityNotFoundException {
        log.info("Trying to delete card :{}", cardId);
        getById(clientId, cardId);
        creditCardDAO.delete(cardId);
    }

    @Override
    public CreditCard updateBalance(Long cardId, Double balance, Long userId) throws CannotUpdateEntityException, EntityNotFoundException {
        log.info("Trying to change card balance", balance, cardId);
        CreditCard card = getById(userId, cardId);
        card.setBalance(card.getBalance() + balance);
        return creditCardDAO.update(card);

    }

    @Override
    public CreditCard getByGreatestBalance(Long userId) throws EntityNotFoundException {
        log.info("Trying to get user card");
        Optional<CreditCard> creditCard = getAllClientCards(userId)
                .stream()
                .max(Comparator.comparing(CreditCard::getBalance));
        if (creditCard.isPresent()) {
            return creditCard.get();
        } else throw new EntityNotFoundException();
    }
}
