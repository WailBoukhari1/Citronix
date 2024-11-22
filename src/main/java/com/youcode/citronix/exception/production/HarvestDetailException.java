package com.youcode.citronix.exception.production;

public class HarvestDetailException extends RuntimeException {
    public HarvestDetailException(String message) {
        super(message);
    }

    public HarvestDetailException(String message, Throwable cause) {
        super(message, cause);
    }
} 