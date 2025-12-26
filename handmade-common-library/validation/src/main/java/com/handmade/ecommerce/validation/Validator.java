package com.handmade.ecommerce.validation;

import jakarta.validation.ValidationException;

public interface Validator<T> {
    /**
     * Validates the given object
     * @param object the object to validate
     * @throws ValidationException if validation fails
     */
    void validate(T object);

    /**
     * Returns the validator name for logging
     */
    String getValidatorName();

    boolean shouldValidate(T object);
}
