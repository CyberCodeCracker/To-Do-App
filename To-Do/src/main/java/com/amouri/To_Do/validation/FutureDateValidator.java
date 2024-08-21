package com.amouri.To_Do.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value != null && value.isAfter(LocalDateTime.now());
    }
}
