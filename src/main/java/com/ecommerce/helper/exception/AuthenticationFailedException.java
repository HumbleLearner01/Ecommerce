package com.ecommerce.helper.exception;

public class AuthenticationFailedException extends IllegalArgumentException{
    public AuthenticationFailedException(String s) {
        super(s);
    }
}
