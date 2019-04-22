package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
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
    private CreditCardService creditCardService = CreditCardServiceImpl.getInstance();

    @Captor
    private ArgumentCaptor<CreditCard> captor;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void cannotGetCardWithWrongId() {
        when(creditCardDAO.getAllClientCards(1L)).thenThrow(new EntityNotFoundException());
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
        List<CreditCard> list = new ArrayList<>();
        list.add(card);
        when(creditCardDAO.getAllClientCards(1L)).thenReturn(list);
        CreditCard card1 = creditCardService.getById(1L, 1L);
        verify(creditCardDAO, times(1)).getAllClientCards(anyLong());
        Assert.assertEquals(card, card1);
    }
}