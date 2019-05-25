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
    @Min(0)
    @Max(10000)
    double totalPrice;

    @Min(value = 0, message = "Id cannot be negative")
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

    @Min(value = 0, message = "Id cannot be negative")
    long cardId;
    @Null
    BigInteger number;
    @Null
    Double balance;

    @Min(value = 0, message = "Id cannot be negative")
    long tourId;
    @Null
    Date settlementDate;
    @Null
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
