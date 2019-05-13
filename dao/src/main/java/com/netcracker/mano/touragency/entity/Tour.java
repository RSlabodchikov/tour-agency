package com.netcracker.mano.touragency.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tours", schema = "tour_agency")
public class Tour extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "eviction_date")
    private Date evictionDate;

    @Column(name = "settlement_date")
    private Date settlementDate;

    @Column(name = "country_name")
    private String country;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "vacant_places")
    private int numberOfClients;

}
