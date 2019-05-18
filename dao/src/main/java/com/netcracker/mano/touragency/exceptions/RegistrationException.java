package com.netcracker.mano.touragency.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }
}
