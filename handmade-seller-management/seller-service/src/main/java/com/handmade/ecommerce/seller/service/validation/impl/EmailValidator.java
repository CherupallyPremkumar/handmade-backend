package com.handmade.ecommerce.seller.service.validation.impl;

import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.validation.ValidationResult;
import com.handmade.ecommerce.seller.service.SellerValidationService;
import com.handmade.ecommerce.seller.service.validation.SellerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Validates email format and uniqueness.
 * Priority: 2
 */
@Component
@Order(2)
public class EmailValidator implements SellerValidator {

    @Autowired
    private SellerValidationService sellerValidationService;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    @Override
    public ValidationResult validate(CreateSellerRequest request) {
        ValidationResult result = new ValidationResult();

        if (request.getEmail() == null) {
            return result; // Already validated by BasicFieldValidator
        }

        // Validate format
        if (!request.getEmail().matches(EMAIL_REGEX)) {
            result.addError("email", "Invalid email format");
            return result; // Don't check uniqueness if format is invalid
        }

        // Check uniqueness
        if (sellerValidationService.existsByEmail(request.getEmail())) {
            result.addError("email", "Email already registered");
        }

        return result;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
