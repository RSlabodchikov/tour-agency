package com.netcracker.mano.touragency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Tour extends BaseEntity {
    private Date evictionDate;
    private Date settlementDate;
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
