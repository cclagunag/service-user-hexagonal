package com.user.user.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(String.format("%s con identificador '%s' no encontrado", resourceName, identifier.toString()));
    }
}
