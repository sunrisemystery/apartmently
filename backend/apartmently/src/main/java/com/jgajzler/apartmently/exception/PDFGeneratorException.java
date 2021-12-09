package com.jgajzler.apartmently.exception;

public class PDFGeneratorException extends RuntimeException {

    public PDFGeneratorException() {

    }

    public PDFGeneratorException(String message) {
        super(message);
    }

    public PDFGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
