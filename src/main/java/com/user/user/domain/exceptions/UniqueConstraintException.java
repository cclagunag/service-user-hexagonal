package com.user.user.domain.exceptions;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(String resourceName, String field, Object value) {
        super(String.format("%s con %s '%s' ya existe",
                resourceName, field, value.toString()));
    }
}
