package com.ecommerce.helper.customvalidation;

import com.ecommerce.helper.customvalidation.validator.ValidateCvv2Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateCvv2Validator.class)
@Target({ElementType.FIELD})
public @interface ValidateCvv2 {
    String message() default "must be exactly 16 digits. provided {givenNumber} digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}