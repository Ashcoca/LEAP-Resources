package com.fidelity.bookdemo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class GoogleBooksUnavailableException extends RuntimeException {

    public GoogleBooksUnavailableException(String message) {
        super(message);
    }
}