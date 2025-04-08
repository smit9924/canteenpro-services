package com.app.canteenpro.exceptions;

public class OTPExpiredException extends RuntimeException{
    public OTPExpiredException(String message) {
        super(message);
    }
}
