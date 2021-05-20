package com.jgajzler.apartmently.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public JwtException jwtException(JwtException e) {
        return e;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body(HttpStatus.UNPROCESSABLE_ENTITY, e));
    }


    private ErrorResponse body(HttpStatus status, Exception e) {
        return new ErrorResponse(
                status,
                new Date(),
                e.getClass().getTypeName(),
                e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.toString()
        );
    }

}
