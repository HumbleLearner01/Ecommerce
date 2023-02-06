package com.ecommerce.helper.customvalidation;


import com.ecommerce.helper.customvalidation.validator.ValidateCardNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = ValidateCardNumberValidator.class)
public @interface ValidateCardNumber {
    String message() default "must be exactly 16 digits. provided {givenNumber} digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}