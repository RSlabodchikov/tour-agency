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
    public List<CreditCard> getAll() {
        log.info("Trying to get all cards");
        return creditCardDAO.getAll();
    }

    @Override
    public List<CreditCard> getAllClientCards(Long userId) throws EntityNotFoundException {
        log.debug("Trying to get all client cards :{}", userId);
        List<CreditCard> creditCards = creditCardDAO.getAllClientCards(userId);
        if (creditCards.isEmpty()) {
            throw new EntityNotFoundException();
        } else return creditCards;
    }

    @Override
    public CreditCard getById(Long clientId, Long cardId) throws EntityNotFoundException {
        log.debug("Trying to get card by id :{}", cardId);
        return creditCardDAO.getClientCard(cardId, clientId);
    }

    @Override
    public CreditCard create(CreditCard creditCard) throws CannotCreateEntityException {
        log.debug("Trying to create credit card with balance :{}", creditCard.getBalance());
        if (creditCard.getBalance() < 0) throw new CannotCreateEntityException();
        creditCard.setNumber(BigInteger.valueOf((Math.abs(random.nextLong()))));
        return creditCardDAO.add(creditCard);
    }

    @Override
    public void delete(Long cardId, Long clientId) throws EntityNotFoundException {
        log.info("Trying to delete card... id :{}", cardId);
        if (!creditCardDAO.checkIfExist(cardId, clientId)) throw new EntityNotFoundException();
        creditCardDAO.delete(cardId);
    }

    @Override
    public CreditCard updateBalance(CreditCard creditCard) throws CannotUpdateEntityException, EntityNotFoundException {
        log.info("Trying to change card balance");
        if (!creditCardDAO.checkIfExist(creditCard.getId(), creditCard.getUserId()))
            throw new EntityNotFoundException();
        if (creditCard.getBalance() < 0) throw new CannotUpdateEntityException();
        return creditCardDAO.update(creditCard);

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
