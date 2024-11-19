 package com.youcode.citronix.exception;

import com.youcode.citronix.exception.base.CitronixException;
import org.springframework.http.HttpStatus;

public class ValidationException extends CitronixException {
    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}