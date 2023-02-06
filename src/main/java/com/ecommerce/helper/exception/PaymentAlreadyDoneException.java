package com.ecommerce.helper.exception;

public class PaymentAlreadyDoneException extends RuntimeException {
    public PaymentAlreadyDoneException(String s) {
        super(s);
    }
}