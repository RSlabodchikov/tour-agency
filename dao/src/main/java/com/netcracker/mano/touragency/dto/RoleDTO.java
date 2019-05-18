package com.netcracker.mano.touragency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    @Min(value = 0, message = "Id cannot be negative")
    long id;
    @NotNull(message = "Role name cannot be null")
    @Size(min = 4, max = 45, message = "Role length should be between 4 - 45")
    String name;
}
