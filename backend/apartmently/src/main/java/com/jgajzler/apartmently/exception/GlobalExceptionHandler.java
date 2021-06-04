package com.jgajzler.apartmently.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> jwtException(JwtException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body(HttpStatus.NOT_ACCEPTABLE, e, ""));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> jwtExpiredException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body(HttpStatus.NOT_ACCEPTABLE, e, "Expired token"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body(HttpStatus.UNPROCESSABLE_ENTITY, e, "Entity not found"));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> authException(InternalAuthenticationServiceException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body(HttpStatus.NOT_FOUND, e, "Bad credentials"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentials(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body(HttpStatus.NOT_FOUND, e, "Bad credentials"));
    }


    private ErrorResponse body(HttpStatus status, Exception e, String message) {
        return new ErrorResponse(
                status,
                new Date(),
                e.getClass().getTypeName(),
                e.getLocalizedMessage() != null ? e.getLocalizedMessage() : message
        );
    }

}
