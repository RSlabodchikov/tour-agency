package com.netcracker.mano.touragency.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "credentials", schema = "tour_agency")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
}