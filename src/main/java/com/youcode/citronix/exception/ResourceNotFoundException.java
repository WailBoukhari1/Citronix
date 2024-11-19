 package com.youcode.citronix.exception;

import com.youcode.citronix.exception.base.CitronixException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CitronixException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}