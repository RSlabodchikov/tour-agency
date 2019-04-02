package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.dao.Impl.BookingDAOImpl;
import com.netcracker.mano.touragency.dao.Impl.CreditCardDAOImpl;
import com.netcracker.mano.touragency.dao.Impl.TourDAOImpl;
import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.interfaces.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {
    private Random random = new Random();
    private CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private BookingDAO bookingDAO = new BookingDAOImpl();
    private TourDAO tourDAO = new TourDAOImpl();

    @Override
    public List<CreditCard> getAllClientCards(Long userId) {
        return creditCardDAO.getAll()
                .stream()
                .filter(a -> a.getUserId() == userId)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<CreditCard> getCardById(Long clientId, Long cardId) {
        return getAllClientCards(clientId)
                .stream()
                .filter(a -> a.getId() == cardId)
                .findFirst();
    }

    @Override
    public void createCard(Double balance, Long id) {
        CreditCard card = new CreditCard();
        card.setBalance(balance);
        card.setUserId(id);
        card.setNumber(random.nextLong());
        creditCardDAO.add(card);
    }

    @Override
    public void deleteCard(Long cardId) {
        creditCardDAO.delete(cardId);
    }
}
