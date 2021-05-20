package com.jgajzler.apartmently.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final HttpStatus status;
    private final Date timestamp;
    private final String type;
    private final String message;

}
