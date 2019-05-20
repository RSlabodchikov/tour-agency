package com.netcracker.mano.touragency.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class CreditCardDTO {
    @Min(value = 0, message = "Id cannot be negative")
    long id;
    @Min(value = 0, message = "Number of card cannot be negative")
    BigInteger number;
    @NotNull(message = "Balance of card cannot be 0")
    @Min(0)
    @Max(value = 1000, message = "Max balance is 1000")
    double balance;

    @Min(0)
    long userId;
    @Null
    String name;
    @Null
    String surname;
    @Null
    String role;
    @Null
    String login;

}
