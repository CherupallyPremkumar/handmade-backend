package com.handmade.ecommerce.seller.service;

import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;

/**
 * Service interface for seller validation logic.
 * This keeps validation logic in the seller module, not in orchestration.
 */
public interface SellerValidationService {


    void validateSellerCreation(CreateSellerRequest request);


    boolean existsByEmail(String email);


    boolean existsByTaxId(String taxId);
}
