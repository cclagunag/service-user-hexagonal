package com.user.user.infraestructure.adapter.handlers;
import com.user.user.domain.exceptions.ResourceNotFoundException;
import com.user.user.domain.exceptions.UniqueConstraintException;
import com.user.user.infraestructure.adapter.DTOS.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(UniqueConstraintException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(UniqueConstraintException ex) {
        return new ErrorResponse(ex.getMessage(), "UNIQUE_CONSTRAINT_VIOLATION", HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return new ErrorResponse(errors, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value());
    }
}