package ru.keller.bidaskanalyzer.exception;

import jakarta.validation.ValidationException;

public class BusinessValidationException extends ValidationException {
    public BusinessValidationException(String message) {
        super(message);
    }
}
