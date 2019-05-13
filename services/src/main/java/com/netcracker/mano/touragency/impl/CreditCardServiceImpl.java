package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import com.netcracker.mano.touragency.repository.CreditCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;


@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {
    private Random random = new Random();

    private CreditCardRepository repository;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CreditCard> getAll() {
        log.info("Trying to get all cards");
        List<CreditCard> creditCards = new ArrayList<>();
        repository.findAll().forEach(creditCards::add);
        return creditCards;
    }

    @Override
    public List<CreditCard> getAllClientCards(Long userId) throws EntityNotFoundException {
        log.debug("Trying to get all client cards :{}", userId);
        List<CreditCard> creditCards = repository.findAllByUser_Id(userId);
        if (creditCards.isEmpty()) {
            throw new EntityNotFoundException();
        } else return creditCards;
    }

    @Override
    public CreditCard getById(Long clientId, Long cardId) throws EntityNotFoundException {
        log.debug("Trying to get card by id :{}", cardId);
        CreditCard creditCard = repository.findByIdAndUser_Id(cardId, clientId);
        if (creditCard == null) throw new EntityNotFoundException();
        return creditCard;
    }

    @Override
    public CreditCard create(CreditCard creditCard) throws CannotCreateEntityException {
        log.debug("Trying to create credit card with balance :{}", creditCard.getBalance());
        if (creditCard.getBalance() < 0) throw new CannotCreateEntityException();
        creditCard.setNumber(BigInteger.valueOf((Math.abs(random.nextLong()))));
        return repository.save(creditCard);
    }

    @Override
    public void delete(Long cardId, Long clientId) throws EntityNotFoundException {
        log.info("Trying to delete card... id :{}", cardId);
        if (!repository.existsByIdAndUser_Id(cardId, clientId)) throw new EntityNotFoundException();
        repository.delete(cardId);
    }

    @Override
    public CreditCard updateBalance(CreditCard creditCard) throws CannotUpdateEntityException, EntityNotFoundException {
        log.info("Trying to change card balance");
        if (!repository.existsByIdAndUser_Id(creditCard.getId(), creditCard.getUser().getId()))
            throw new EntityNotFoundException();
        if (creditCard.getBalance() < 0) throw new CannotUpdateEntityException();
        return repository.save(creditCard);

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
