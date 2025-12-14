package com.handmade.ecommerce.seller.service.validation;

import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.validation.ValidationResult;

/**
 * Strategy interface for seller validation.
 * Implement this interface to add new validation logic without modifying
 * existing code.
 * 
 * Example validators:
 * - BasicFieldValidator (required fields)
 * - EmailValidator (format + uniqueness)
 * - GstinFormatValidator (format + checksum)
 * - GstinApiValidator (API verification - Phase 2)
 * - VideoKycValidator (video verification - Phase 3)
 */
public interface SellerValidator {

    /**
     * Validates a specific aspect of seller data.
     * 
     * @param request The seller creation request to validate
     * @return ValidationResult with errors (if any)
     */
    ValidationResult validate(CreateSellerRequest request);

    /**
     * Priority order for execution (lower = higher priority).
     * 
     * Recommended order:
     * 1-10: Basic validations (required fields, format)
     * 11-20: Business logic validations (duplicates, rules)
     * 21-30: External API validations (GSTIN, PAN)
     * 31+: Advanced validations (video KYC, etc.)
     * 
     * @return Priority order
     */
    int getOrder();

    /**
     * Whether this validator is currently enabled.
     * Can be controlled via feature flags.
     * 
     * @return true if enabled
     */
    default boolean isEnabled() {
        return true;
    }

    /**
     * Name of this validator for logging/debugging.
     * 
     * @return Validator name
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
