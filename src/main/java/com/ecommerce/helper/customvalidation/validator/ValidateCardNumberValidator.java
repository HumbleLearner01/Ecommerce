package com.ecommerce.helper.customvalidation.validator;

import com.ecommerce.helper.customvalidation.ValidateCardNumber;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ValidateCardNumberValidator implements ConstraintValidator<ValidateCardNumber, String> {

    @Override
    public boolean isValid(String givenValue, ConstraintValidatorContext constraintValidatorContext) {
        String replace = givenValue.replaceAll("[^0-9]", "");
        if (!replace.matches("^\\d{16}$")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Must be exactly 16 digits, Provided " + replace.length() + " digits.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}