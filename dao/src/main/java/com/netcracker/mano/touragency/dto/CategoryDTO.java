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
public class CategoryDTO {
    @Min(value = 0, message = "Id cannot be negative")
    long id;
    @NotNull(message = "Category name cannot be null")
    @Size(min = 5, max = 45, message = "Category name length should be between 5 and 45")
    String name;
}
