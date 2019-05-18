package com.netcracker.mano.touragency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;


@Data
@NoArgsConstructor
public class TourDTO {
    @Min(value = 0, message = "Id cannot be negative")
    long id;
    @Future(message = "Settlement date should be in future")
    @NotNull(message = "Settlement date cannot be null")
    Date settlementDate;
    @Future(message = "Eviction date should be in future")
    @NotNull(message = "Eviction date cannot be null")
    Date evictionDate;
    @NotNull
    @Size(min = 5, max = 45)
    String country;
    @NotNull
    @Min(50)
    @Max(2000)
    double price;
    @Size(max = 100)
    String description;
    @NotNull
    @Min(2)
    @Max(128)
    int numberOfClients;
    @NotNull
    long categoryId;
    @Null
    String category;
}
