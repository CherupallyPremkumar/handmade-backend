package com.handmade.ecommerce.validation;

import org.springframework.stereotype.Component;

import java.util.List;

public class ValidationOrchestrator<T> {

    private final List<Validator<T>> validators;

    public ValidationOrchestrator(List<Validator<T>> validators) {
        this.validators = validators;
    }

    public void validate(T object) {
        for (Validator<T> validator : validators) {
            validator.validate(object);
        }
    }
}