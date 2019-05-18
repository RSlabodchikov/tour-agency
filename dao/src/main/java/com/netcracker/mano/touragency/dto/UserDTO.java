package com.netcracker.mano.touragency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserDTO {
    @Min(0)
    long id;
    @NotNull
    @Size(min = 5, max = 45)
    String name;
    @NotNull
    @Size(min = 5, max = 45)
    String surname;
    long roleId;
    String role;
    Boolean isBlocked;
    long credentialsId;
    @NotNull
    @Size(min = 5, max = 45)
    String login;
    @NotNull
    @Size(min = 5, max = 45)
    String password;
}
