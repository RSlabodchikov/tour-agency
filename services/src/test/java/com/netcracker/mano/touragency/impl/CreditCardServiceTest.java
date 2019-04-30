package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreditCardServiceTest {
    @Mock
    private CreditCardDAO creditCardDAO;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Captor
    private ArgumentCaptor<CreditCard> captor;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void cannotGetCardWithWrongId() {
        when(creditCardDAO.getClientCard(1L, 1L)).thenThrow(new EntityNotFoundException());
        creditCardService.getById(1L, 1L);
        verify(creditCardDAO, times(1)).getAllClientCards(anyLong());
    }

    @Test
    @SneakyThrows
    public void getById() {
        CreditCard card = CreditCard.builder()
                .balance(500)
                .userId(1L)
                .build();
        card.setId(1L);
        when(creditCardDAO.getClientCard(1L, 1L)).thenReturn(card);
        CreditCard card1 = creditCardService.getById(1L, 1L);
        verify(creditCardDAO, times(1)).getClientCard(anyLong(), anyLong());
        Assert.assertEquals(card, card1);
    }

    @Test
    @SneakyThrows
    public void createCard() {
        CreditCard card = CreditCard.builder()
                .balance(500)
                .userId(1L)
                .build();
        when(creditCardDAO.add(card)).thenReturn(card);
        creditCardService.create(500D, 1L);
        verify(creditCardDAO, times(1)).add(captor.capture());
        Assert.assertEquals(card.getBalance(), captor.getValue().getBalance(), 0);
    }

    @Test(expected = CannotCreateEntityException.class)
    @SneakyThrows
    public void cannotCreateCardWithNegativeBalance() {
        CreditCard card = CreditCard.builder()
                .balance(500)
                .userId(1L)
                .build();
        when(creditCardDAO.add(card)).thenReturn(card);
        creditCardService.create(-400D, 1L);
        verify(creditCardDAO, times(1)).add(any());
    }

    @Test
    @SneakyThrows
    public void delete() {
        CreditCard card = CreditCard.builder()
                .balance(500)
                .userId(1L)
                .build();
        card.setId(1L);
        when(creditCardDAO.getClientCard(1L, 1L)).thenReturn(card);
        creditCardService.delete(1L, 1L);
        verify(creditCardDAO, times(1)).delete(1L);
        verify(creditCardDAO, times(1)).getClientCard(1L, 1L);
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void cannotDeleteWithWrongId() {
        when(creditCardDAO.getClientCard(2L, 1L)).thenThrow(new EntityNotFoundException());
        creditCardService.delete(2L, 1L);
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void cannotGetCardWithGreatestBalanceIFNoCardExisted() {
        List<CreditCard> list = new ArrayList<>();
        when(creditCardDAO.getAllClientCards(anyLong())).thenReturn(list);
        creditCardService.getByGreatestBalance(1L);
        verify(creditCardDAO, times(1)).getAllClientCards(1L);
    }

    @Test
    @SneakyThrows
    public void getCardWithGreatestBalance() {
        CreditCard card = CreditCard.builder()
                .balance(500)
                .userId(1L)
                .build();
        card.setId(1L);
        CreditCard card1 = CreditCard.builder()
                .balance(1000)
                .userId(1L)
                .build();
        card1.setId(2L);
        List<CreditCard> list = new ArrayList<>();
        list.add(card);
        list.add(card1);
        when(creditCardDAO.getAllClientCards(1L)).thenReturn(list);
        CreditCard result = creditCardService.getByGreatestBalance(1L);
        verify(creditCardDAO, times(1)).getAllClientCards(1L);
        Assert.assertEquals(card1.getBalance(), result.getBalance(), 0.1);
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void cannotUpdateNotExistingCard() {
        when(creditCardDAO.getClientCard(1L, 1L)).thenThrow(new EntityNotFoundException());
        creditCardService.updateBalance(1L, 400D, 1L);
        verify(creditCardDAO, times(1)).getAllClientCards(1L);
    }

    @Test(expected = CannotUpdateEntityException.class)
    @SneakyThrows
    public void cannotUpdateCard() {
        CreditCard card = CreditCard.builder()
                .balance(1000D)
                .userId(1L)
                .build();
        card.setId(1L);
        when(creditCardDAO.getClientCard(1L, 1L)).thenReturn(card);
        when(creditCardDAO.update(any())).thenThrow(new CannotUpdateEntityException());
        creditCardService.updateBalance(1L, -2000D, 1L);
    }

    @Test
    @SneakyThrows
    public void updateBalance() {
        CreditCard card = CreditCard.builder()
                .balance(1000)
                .userId(1L)
                .build();
        card.setId(1L);
        when(creditCardDAO.getClientCard(1L, 1L)).thenReturn(card);
        when(creditCardDAO.update(card)).thenReturn(card);
        creditCardService.updateBalance(1L, 200D, 1L);
        verify(creditCardDAO).update(captor.capture());
        Assert.assertEquals(card.getBalance(), captor.getValue().getBalance(), 1);

    }

}