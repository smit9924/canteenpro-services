package com.app.canteenpro.exceptions;

public class EmailTransmissionFailedException extends RuntimeException{
    public EmailTransmissionFailedException(String message) {
        super(message);
    }
}
