package com.user.user.infraestructure.adapter.DTOS;

public record ErrorResponse(
        String message,
        String errorCode,
        int status
) {}