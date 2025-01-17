package ru.keller.bidaskanalyzer.validator;

@FunctionalInterface
public interface ValidationRule<T> {
    void validate(T input);
}
