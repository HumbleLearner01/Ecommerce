package com.ecommerce.helper.exception;

public class PaymentNotValidException extends RuntimeException {
    public PaymentNotValidException(String paymentName, String fieldValue, String paymentName2, String fieldValue2) {
        super(String.format("'%s : %s' and '%s : %s' is not valid!",paymentName,fieldValue,paymentName2,fieldValue2));
    }
}