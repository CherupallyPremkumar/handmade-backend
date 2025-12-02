package com.handmade.ecommerce.seller.service.validation.impl;

import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.validation.ValidationResult;
import com.handmade.ecommerce.seller.service.validation.SellerValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Validates required fields in seller creation request.
 * Priority: 1 (highest - run first)
 */
@Component
@Order(1)
public class BasicFieldValidator implements SellerValidator {

    @Override
    public ValidationResult validate(CreateSellerRequest request) {
        ValidationResult result = new ValidationResult();

        // Owner information
        if (request.getSellerName() == null || request.getSellerName().trim().isEmpty()) {
            result.addError("sellerName", "Seller name is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            result.addError("email", "Email is required");
        }

        // Business information
        if (request.getBusinessName() == null || request.getBusinessName().trim().isEmpty()) {
            result.addError("businessName", "Business name is required");
        }

        // Validate field lengths
        if (request.getSellerName() != null && request.getSellerName().length() < 3) {
            result.addError("sellerName", "Seller name must be at least 3 characters");
        }

        if (request.getSellerName() != null && request.getSellerName().length() > 100) {
            result.addError("sellerName", "Seller name must not exceed 100 characters");
        }

        if (request.getBusinessName() != null && request.getBusinessName().length() < 3) {
            result.addError("businessName", "Business name must be at least 3 characters");
        }

        if (request.getBusinessName() != null && request.getBusinessName().length() > 100) {
            result.addError("businessName", "Business name must not exceed 100 characters");
        }

        return result;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
