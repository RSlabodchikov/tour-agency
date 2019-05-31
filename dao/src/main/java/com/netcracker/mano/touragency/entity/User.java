package com.netcracker.mano.touragency.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users", schema = "tour_agency")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id", referencedColumnName = "id")
    private Credentials credentials;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

}
