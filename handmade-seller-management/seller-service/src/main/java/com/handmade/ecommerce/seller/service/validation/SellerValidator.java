package com.handmade.ecommerce.seller.service.validation;

import com.handmade.ecommerce.seller.dto.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.validation.ValidationResult;

/**
 * Interface for all seller creation request validators.
 */
public interface SellerValidator {
    
    /**
     * Validates the request.
     * 
     * @param request The request to validate
     * @return Result of the validation
     */
    ValidationResult validate(CreateSellerRequest request);

    /**
     * Returns the execution order for this validator.
     * 
     * @return priority (lower is higher priority)
     */
    int getOrder();

    /**
     * Whether this validator is enabled.
     * 
     * @return true if enabled
     */
    default boolean isEnabled() {
        return true;
    }

    /**
     * Name of the validator for logging.
     * 
     * @return name
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
