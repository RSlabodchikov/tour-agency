package com.netcracker.mano.touragency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Booking extends BaseEntity {
    private int numberOfClients;
    private double totalPrice;
    private long userId;
    private long tourId;

    public Booking() {
        super();
        super.type = "Booking";
    }

    @Override
    public String toString() {
        return "Booking{" +
                "numberOfClients=" + numberOfClients +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", tourId=" + tourId +
                ", id=" + id +
                '}';
    }
}
