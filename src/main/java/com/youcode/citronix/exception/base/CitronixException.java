 package com.youcode.citronix.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CitronixException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public CitronixException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}