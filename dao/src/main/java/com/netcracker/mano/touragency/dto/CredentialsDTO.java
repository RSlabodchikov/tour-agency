package com.netcracker.mano.touragency.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class CredentialsDTO {
    @Min(value = 0, message = "Id cannot be negative")
    long id;
    @NotNull(message = "Password cannot be null")
    @Size(min = 5, max = 45, message = "Login length should be between 5 and 45")
    String login;
    @Size(min = 5, max = 100, message = "Password length should be between 5 and 100")
    @NotNull(message = "Password cannot be null")
    String password;
}
