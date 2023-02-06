package com.ecommerce.helper.exception.exception_handler;

import com.ecommerce.helper.exception.AuthenticationFailedException;
import com.ecommerce.helper.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = CustomException.class)
    public final ResponseEntity<?> handleCustomException(CustomException exception) {
        return new ResponseEntity<>(exception.getMessage() + "\n" + exception.getCause(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthenticationFailedException.class)
    public final ResponseEntity<?> handleAuthenticationFailed(AuthenticationFailedException exception) {
        return new ResponseEntity<>(exception.getMessage() + "\n" + exception.getCause(), HttpStatus.UNAUTHORIZED);
    }
}