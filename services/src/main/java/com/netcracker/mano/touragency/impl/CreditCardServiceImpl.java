package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.CreditCardConverter;
import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.repository.CreditCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {
    private Random random = new Random();

    private CreditCardRepository repository;

    private CreditCardConverter converter;

    private UserService service;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository repository, CreditCardConverter converter, UserService service) {
        this.repository = repository;
        this.converter = converter;
        this.service = service;
    }

    @Override
    public List<CreditCardDTO> getAll() {
        log.info("Trying to get all cards");
        return repository.findAll()
                .stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditCardDTO> getAllClientCards(String login) {
        log.debug("Trying to get all client cards :{}", login);
        List<CreditCard> creditCards = repository.findAllByUser_Credentials_Login(login);
        if (creditCards.isEmpty()) {
            throw new EntityNotFoundException("User with this login have no credit cards");
        } else return creditCards.stream().map(converter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CreditCardDTO getById(String login, Long id) {
        log.debug("Trying to get card by id :{}", id);
        CreditCard creditCard = repository.findByIdAndUser_Credentials_Login(id, login);
        if (creditCard == null) throw new EntityNotFoundException("Cannot find credit card by this parameters");
        return converter.convertToDTO(creditCard);
    }

    @Override
    public CreditCardDTO create(CreditCardDTO creditCardDTO) {
        log.debug("Trying to create credit card with balance :{}", creditCardDTO.getBalance());
        CreditCard creditCard = converter.convertToEntity(creditCardDTO);
        if (creditCard.getBalance() < 0)
            throw new CannotCreateEntityException("Cannot create card with negative balance");
        creditCard.setUser(service.findByLogin(creditCardDTO.getLogin()));
        creditCard.setNumber(BigInteger.valueOf((Math.abs(random.nextLong()))));
        creditCard = repository.save(creditCard);
        creditCard.getUser().getCredentials().setLogin(creditCardDTO.getLogin());
        return converter.convertToDTO(creditCard);
    }

    @Override
    public void delete(Long id, String login) {
        log.info("Trying to delete card... id :{}", id);
        if (!repository.existsByIdAndUser_Credentials_Login(id, login))
            throw new EntityNotFoundException("You are trying to delete not existing card");
        repository.delete(id);
    }

    @Override
    public CreditCardDTO updateBalance(CreditCardDTO creditCard) {
        log.info("Trying to change card balance");
        if (!repository.existsByIdAndUser_Credentials_Login(creditCard.getId(), creditCard.getLogin()))
            throw new EntityNotFoundException("You are trying to update not existing credit card");
        if (creditCard.getBalance() < 0) throw new CannotUpdateEntityException();
        return converter.convertToDTO(repository.save(converter.convertToEntity(creditCard)));
    }

    @Override
    public CreditCardDTO getByGreatestBalance(String login) {
        log.info("Trying to get user card");
        Optional<CreditCardDTO> creditCard = getAllClientCards(login)
                .stream()
                .max(Comparator.comparing(CreditCardDTO::getBalance));
        if (creditCard.isPresent()) {
            return creditCard.get();
        } else throw new EntityNotFoundException("User with this login have no credit cards");
    }
}
