package ru.keller.bidaskanalyzer.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsinValidator implements ConstraintValidator<ValidIsin, String> {

    private static final String ISIN_PATTERN = "^[A-Z0-9]{12}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches(ISIN_PATTERN);
    }
}
