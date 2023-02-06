package com.ecommerce.helper.exception.exception_handler;


import com.ecommerce.helper.exception.*;
import com.ecommerce.helper.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            response.put(field, defaultMessage);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentNotValidException.class)
    public ResponseEntity<ApiResponse> paymentNotValidExceptionHandler(PaymentNotValidException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(RedditException.class)
    public ResponseEntity<ApiResponse> redditExceptionHandler(RedditException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiResponse> authenticationFailedExceptionHandler(AuthenticationFailedException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentAlreadyDoneException.class)
    public ResponseEntity<ApiResponse> paymentAlreadyDoneExceptionHandler(PaymentAlreadyDoneException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCancellationException.class)
    public ResponseEntity<ApiResponse> orderCancellationExceptionHandler(OrderCancellationException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
    }
}