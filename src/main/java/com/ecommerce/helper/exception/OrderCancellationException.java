package com.ecommerce.helper.exception;

public class OrderCancellationException extends RuntimeException {
    public OrderCancellationException(String s) {
        super(s);
    }
}