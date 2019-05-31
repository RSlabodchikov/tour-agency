package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.CreditCardConverter;
import com.netcracker.mano.touragency.converter.UserConverter;
import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.repository.CreditCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {
    private Random random = new Random();

    private CreditCardRepository repository;

    private CreditCardConverter converter;

    private UserService service;

    private UserConverter userConverter;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository repository, CreditCardConverter converter, UserService service, UserConverter userConverter) {
        this.repository = repository;
        this.converter = converter;
        this.service = service;
        this.userConverter = userConverter;
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
    public List<CreditCardDTO> getAllClientCards() {
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        log.debug("Trying to get all client cards :{}", login);
        List<CreditCard> creditCards = repository.findAllByUser_Credentials_Login(login);
        if (creditCards.isEmpty()) {
            throw new EntityNotFoundException("User with this login have no credit cards");
        }
        return creditCards.stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CreditCardDTO getById(Long id) {
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        log.debug("Trying to get card by id :{}", id);
        CreditCard creditCard = repository.findByIdAndUser_Credentials_Login(id, login);
        if (creditCard == null) throw new EntityNotFoundException("Cannot find credit card by this parameters");
        return converter.convertToDTO(creditCard);
    }

    @Override
    public CreditCardDTO create(CreditCardDTO creditCardDTO) {
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        log.debug("Trying to create credit card with balance :{}", creditCardDTO.getBalance());
        CreditCard creditCard = converter.convertToEntity(creditCardDTO);
        creditCard.setUser(userConverter.convertToEntity(service.findByLogin(login)));
        creditCard.setNumber(BigInteger.valueOf((Math.abs(random.nextLong()))));
        creditCard = repository.save(creditCard);
        return converter.convertToDTO(creditCard);
    }

    @Override
    public void delete(Long id) {
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        log.info("Trying to delete card... id :{}", id);
        if (!repository.existsByIdAndUser_Credentials_Login(id, login))
            throw new EntityNotFoundException("You are trying to delete not existing card");
        repository.delete(id);
    }

    @Override
    public CreditCardDTO updateBalance(CreditCardDTO creditCardDTO) {
        log.info("Trying to change card balance");
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        CreditCard creditCard;
        if ((creditCard = (repository.findByIdAndUser_Credentials_Login(creditCardDTO.getId(), login))) == null)
            throw new EntityNotFoundException("You are trying to update not existing credit card");
        creditCard.setBalance(creditCardDTO.getBalance());
        if (creditCard.getBalance() < 0) throw new CannotUpdateEntityException();
        return converter.convertToDTO(repository.save(creditCard));
    }

    @Override
    public CreditCardDTO getByGreatestBalance() {
        log.info("Trying to get best user card");
        Optional<CreditCardDTO> creditCard = getAllClientCards()
                .stream()
                .max(Comparator.comparing(CreditCardDTO::getBalance));
        if (creditCard.isPresent()) {
            return creditCard.get();
        } else throw new EntityNotFoundException("User with this login have no credit cards");
    }
}
