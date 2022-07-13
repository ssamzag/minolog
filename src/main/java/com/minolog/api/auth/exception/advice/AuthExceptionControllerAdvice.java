package com.minolog.api.auth.exception.advice;

import com.minolog.api.auth.dto.AuthErrorResult;
import com.minolog.api.auth.service.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionControllerAdvice {
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<AuthErrorResult> authorizationExceptionHandler(AuthorizationException e) {
        return new ResponseEntity(new AuthErrorResult(HttpStatus.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
