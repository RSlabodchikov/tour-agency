package com.netcracker.mano.touragency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User extends BaseEntity {
    private Credentials credentials;
    private String name;
    private String surname;
    private Role role;

    public User() {
        super();
        super.type = "User";
    }

    @Override
    public String toString() {
        return "User{" +
                "credentials=" + credentials +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", id=" + id +
                '}';
    }
}