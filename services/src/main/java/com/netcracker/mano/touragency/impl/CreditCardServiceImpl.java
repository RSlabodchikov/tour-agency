package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.dao.Impl.CreditCardDAOImpl;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.interfaces.CreditCardService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class CreditCardServiceImpl implements CreditCardService {
    private Random random = new Random();
    private CreditCardDAO creditCardDAO = new CreditCardDAOImpl();

    private static CreditCardServiceImpl instance;

    private CreditCardServiceImpl() {
    }

    public static CreditCardServiceImpl getInstance() {
        if (instance == null) {
            instance = new CreditCardServiceImpl();
        }
        return instance;
    }

    @Override
    public List<CreditCard> getAllClientCards(Long userId) {
        return creditCardDAO.getAll()
                .stream()
                .filter(a -> a.getUserId() == userId)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<CreditCard> getById(Long clientId, Long cardId) {
        return getAllClientCards(clientId)
                .stream()
                .filter(a -> a.getId() == cardId)
                .findFirst();
    }

    @Override
    public void create(Double balance, Long id) {
        if (balance < 0) return;
        CreditCard card = new CreditCard();
        card.setBalance(balance);
        card.setUserId(id);
        card.setNumber(random.nextLong());
        creditCardDAO.add(card);
    }

    @Override
    public void delete(Long cardId, Long clientId) {
        if (getById(clientId, cardId).isPresent()) {
            creditCardDAO.delete(cardId);
        }

    }

    @Override
    public CreditCard updateBalance(Long cardId, Double balance, Long userId) {
        Optional<CreditCard> card = getById(userId, cardId);
        if (card.isPresent()) {
            card.get().setBalance(card.get().getBalance() + balance);
            return creditCardDAO.update(card.get());
        }
        return null;
    }

    @Override
    public Optional<CreditCard> getByGreatestBalance(Long userId) {
        return getAllClientCards(userId)
                .stream()
                .max(Comparator.comparing(CreditCard::getBalance));
    }
}
