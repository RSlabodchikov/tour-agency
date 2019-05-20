package com.netcracker.mano.touragency.converter;

import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CreditCardConverter {
    public CreditCard convertToEntity(CreditCardDTO creditCardDTO) {
        User user = User.builder()
                .id(creditCardDTO.getUserId())
                .credentials(Credentials.builder().login(creditCardDTO.getLogin()).build())
                .build();
        return CreditCard.builder()
                .id(creditCardDTO.getId())
                .number(creditCardDTO.getNumber())
                .balance(creditCardDTO.getBalance())
                .user(user)
                .build();
    }

    public CreditCardDTO convertToDTO(CreditCard creditCard) {
        CreditCardDTO dto = new CreditCardDTO();
        dto.setId(creditCard.getId());
        dto.setNumber(creditCard.getNumber());
        dto.setBalance(creditCard.getBalance());

        dto.setUserId(creditCard.getUser().getId());
        dto.setName(creditCard.getUser().getName());
        dto.setSurname(creditCard.getUser().getSurname());
        dto.setLogin(creditCard.getUser().getCredentials().getLogin());
        dto.setRole(creditCard.getUser().getRole().getName());

        return dto;
    }
}
