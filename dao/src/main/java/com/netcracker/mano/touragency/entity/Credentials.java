package com.netcracker.mano.touragency.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Credentials {
    String login;
    String password;
}