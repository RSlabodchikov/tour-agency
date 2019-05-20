package com.netcracker.mano.touragency.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Data
class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;
}