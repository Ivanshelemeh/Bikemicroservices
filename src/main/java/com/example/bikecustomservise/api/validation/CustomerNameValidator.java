package com.example.bikecustomservise.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomerNameValidator implements ConstraintValidator<CustomNameValid,String> {

    private final String PATTERN_NAME_VALID="[a-zA-Z0-9_.-]";
    @Override
    public boolean isValid(String customerName, ConstraintValidatorContext constraintValidatorContext) {
        return customerName.matches(PATTERN_NAME_VALID);
    }
}

