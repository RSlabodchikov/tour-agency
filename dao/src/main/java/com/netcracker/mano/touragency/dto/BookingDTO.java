package com.netcracker.mano.touragency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigInteger;
import java.sql.Date;

@Data
@NoArgsConstructor
public class BookingDTO {
    @Null
    long id;
    @NotNull
    @Min(1)
    @Max(10)
    int numberOfClients;
    @Null
    double totalPrice;

    @NotNull
    long userId;
    @Null
    String userName;
    @Null
    String userSurname;
    @Null
    long roleId;
    @Null
    String role;
    @Null
    Boolean isBlocked;
    @Null
    long credentialsId;
    @Null
    String login;
    @Null
    String password;

    @NotNull
    long cardId;
    @Null
    BigInteger number;
    @Null
    Double balance;

    @NotNull
    long tourId;
    @Null
    @Future
    Date settlementDate;
    @Null
    @Future
    Date evictionDate;
    @Null
    String country;
    @Null
    double price;
    @Null
    String description;
    @Null
    int numberOfClientsInTour;
    @Null
    long categoryId;
    @Null
    String category;

}
