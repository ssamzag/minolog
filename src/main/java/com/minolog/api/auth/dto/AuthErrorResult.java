package com.minolog.api.auth.dto;

import org.springframework.http.HttpStatus;

public class AuthErrorResult {
    private HttpStatus httpStatus;

    private String message;

    public AuthErrorResult(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
