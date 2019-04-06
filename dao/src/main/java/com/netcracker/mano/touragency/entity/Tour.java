package com.netcracker.mano.touragency.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netcracker.mano.touragency.LocalDateFormat.LocalDateDeserializer;
import com.netcracker.mano.touragency.LocalDateFormat.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Tour extends BaseEntity {
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate evictionDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate settlementDate;
    private String country;
    private Category category;
    private Double price;
    private String description;
    private int numberOfClients;



    public Tour() {
        super();
        super.type = "Tour";
    }

    @Override
    public String toString() {
        return "Tour{" +
                "evictionDate=" + evictionDate +
                ", settlementDate=" + settlementDate +
                ", country='" + country + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", numberOfClients=" + numberOfClients +
                ", id=" + id +
                '}';
    }
}
