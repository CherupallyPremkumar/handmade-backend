package com.handmade.ecommerce.seller.service.validation.impl;

import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.validation.ValidationResult;
import com.handmade.ecommerce.seller.service.validation.SellerValidator;
import com.handmade.ecommerce.gstin.service.GstinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Validates GSTIN format and checksum (no API call in MVP).
 * Priority: 3
 * 
 * GSTIN Format: 22AAAAA0000A1Z5 (15 characters)
 * - Positions 1-2: State Code (01-37)
 * - Positions 3-12: PAN (10 characters)
 * - Position 13: Entity Number (1-9, A-Z)
 * - Position 14: 'Z' (default)
 * - Position 15: Check digit
 */
@Component
@Order(3)
public class GstinFormatValidator implements SellerValidator {

    @Autowired
    private GstinService gstinService;

    @Override
    public ValidationResult validate(CreateSellerRequest request) {
        ValidationResult result = new ValidationResult();

        String gstin = request.getTaxId();

        // GSTIN is optional in MVP (for individual sellers)
        if (gstin == null || gstin.trim().isEmpty()) {
            return result;
        }

        // Delegate to GstinService
        if (!gstinService.validateFormat(gstin)) {
            result.addError("taxId", "Invalid GSTIN format or checksum");
        }

        return result;
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
