package com.handmade.ecommerce.seller.api;

import com.handmade.ecommerce.seller.dto.CreateSellerRequest;

/**
 * Service for validating seller creation and updates.
 */
public interface SellerValidationService {
    void validateSellerCreation(CreateSellerRequest request);

    boolean existsByEmail(String email);

    boolean existsByTaxId(String taxId);
}
