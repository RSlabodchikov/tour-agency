package com.netcracker.mano.touragency.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CannotUpdateEntityException extends RuntimeException {
    public CannotUpdateEntityException(String message) {
        super(message);
    }
}
