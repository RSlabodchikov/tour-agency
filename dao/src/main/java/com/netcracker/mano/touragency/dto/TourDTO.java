package com.netcracker.mano.touragency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TourDTO {
    @Min(value = 0, message = "Id cannot be negative")
    long id;
    @Future(message = "Settlement date should be in future")
    @NotNull(message = "Settlement date cannot be null")
    Date settlementDate;
    @Future(message = "Eviction date should be in future")
    @NotNull(message = "Eviction date cannot be null")
    Date evictionDate;
    @NotNull(message = "Country name cannot be null")
    @Size(min = 5, max = 45, message = "Country name length should be between 5 and 45")
    String country;
    @Min(value = 50, message = "Price cannot be less then 50")
    @Max(value = 2000, message = "Price cannot be more then 2000")
    double price;
    @Size(max = 200, message = "Description cannot more then 200 symbols")
    String description;
    @Min(value = 2, message = "Number of clients cannot be less then 2")
    @Max(value = 128, message = "Number of clients cannot be more then 128")
    int numberOfClients;
    @Min(1)
    @Max(5)
    long categoryId;
    @Null(message = "You should pass on only id of category")
    String category;
}
