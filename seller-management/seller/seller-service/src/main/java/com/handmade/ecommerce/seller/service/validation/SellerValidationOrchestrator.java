package com.handmade.ecommerce.seller.service.validation;

import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Orchestrates multiple validators using the Strategy pattern.
 * Validators are executed in order of priority and results are merged.
 * 
 * Benefits:
 * - Easy to add new validators (just implement SellerValidator)
 * - Validators can be enabled/disabled via feature flags
 * - Clear separation of concerns
 * - Testable in isolation
 */
@Service
public class SellerValidationOrchestrator {

    @Autowired
    private List<SellerValidator> validators;

    /**
     * Validates seller request using all enabled validators.
     * Validators are executed in order of priority (lower = higher priority).
     * 
     * @param request The seller creation request
     * @return Merged validation result from all validators
     */
    public ValidationResult validateAll(CreateSellerRequest request) {
        ValidationResult mergedResult = new ValidationResult();

        validators.stream()
                .filter(SellerValidator::isEnabled)
                .sorted(Comparator.comparingInt(SellerValidator::getOrder))
                .forEach(validator -> {
                    System.out.println("Running validator: " + validator.getName());
                    ValidationResult result = validator.validate(request);
                    mergedResult.merge(result);
                });

        return mergedResult;
    }

    /**
     * Validates and throws exception if validation fails.
     * 
     * @param request The seller creation request
     * @throws IllegalArgumentException if validation fails
     */
    public void validateAndThrow(CreateSellerRequest request) {
        ValidationResult result = validateAll(request);

        if (!result.isValid()) {
            // Build error message from all errors
            StringBuilder errorMessage = new StringBuilder("Validation failed: ");
            result.getErrors().forEach(error -> errorMessage.append(error.getField())
                    .append(": ")
                    .append(error.getMessage())
                    .append("; "));
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
