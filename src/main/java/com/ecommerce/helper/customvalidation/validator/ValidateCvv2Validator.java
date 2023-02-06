package com.ecommerce.helper.customvalidation.validator;

import com.ecommerce.helper.customvalidation.ValidateCvv2;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ValidateCvv2Validator implements ConstraintValidator<ValidateCvv2,String> {
    @Override
    public boolean isValid(String givenValue, ConstraintValidatorContext constraintValidatorContext) {
        String replace = givenValue.replaceAll("[^0-9]", "");
        if (!replace.matches("^\\d{3,4}$")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Must be either 3 or 4 digits. Provided " + givenValue.length() + " digits.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}