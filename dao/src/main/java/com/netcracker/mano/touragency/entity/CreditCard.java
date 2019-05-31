package com.netcracker.mano.touragency.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credit_cards")
public class CreditCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "number")
    private BigInteger number;

    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
