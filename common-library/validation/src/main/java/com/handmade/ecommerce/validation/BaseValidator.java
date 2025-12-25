package com.handmade.ecommerce.validation;

import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseValidator<T> implements Validator<T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void validate(T object) {
        if (!shouldValidate(object)) {
            log.debug("{} - Skipping validation", getValidatorName());
            return;
        }
        log.debug("{} - Starting validation", getValidatorName());

        try {
            doValidate(object);
            log.debug("{} - Validation passed", getValidatorName());
        } catch (ValidationException e) {
            log.error("{} - Validation failed: {}", getValidatorName(), e.getMessage());
            throw e;
        }
    }

    protected abstract void doValidate(T object);

    @Override
    public String getValidatorName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean shouldValidate(T object) {
        return false;
    }
}
