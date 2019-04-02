package com.netcracker.mano.touragency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditCard extends BaseEntity {
    private long number;
    private double balance;
    private long userId;
    public CreditCard() {
        super();
        super.type = "CreditCard";
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number=" + number +
                ", balance=" + balance +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
