package com.youcode.citronix.exception.farm;

import org.springframework.http.HttpStatus;

import com.youcode.citronix.exception.base.CitronixException;

public class FarmException extends CitronixException {
    public FarmException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
} 