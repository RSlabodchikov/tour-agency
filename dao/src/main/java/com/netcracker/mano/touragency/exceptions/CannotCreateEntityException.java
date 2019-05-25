package com.netcracker.mano.touragency.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CannotCreateEntityException extends RuntimeException {
    public CannotCreateEntityException(String message) {
        super(message);
    }
}
