package com.netcracker.mano.touragency.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookings", schema = "tour_agency")
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "number_of_clients")
    private int numberOfClients;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private CreditCard card;
}
