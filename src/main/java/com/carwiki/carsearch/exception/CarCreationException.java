package com.carwiki.carsearch.exception;

public class CarCreationException extends RuntimeException{
    public CarCreationException(String message) {
        super(message);
    }

    public CarCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
