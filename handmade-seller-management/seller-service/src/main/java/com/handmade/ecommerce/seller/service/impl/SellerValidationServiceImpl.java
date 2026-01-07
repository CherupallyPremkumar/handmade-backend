package com.handmade.ecommerce.seller.service.impl;

import com.handmade.ecommerce.seller.dto.CreateSellerRequest;
import com.handmade.ecommerce.seller.api.SellerValidationService;
import com.handmade.ecommerce.seller.service.store.SellerEntityStore;
import com.handmade.ecommerce.seller.service.validation.SellerValidationOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of seller validation logic using Strategy pattern.
 * Delegates to SellerValidationOrchestrator which chains multiple validators.
 * 
 * Benefits:
 * - Easy to add new validators without modifying this class
 * - Validators can be enabled/disabled via feature flags
 * - Each validator is testable in isolation
 */
@Service
public class SellerValidationServiceImpl implements SellerValidationService {

    @Autowired
    private SellerEntityStore sellerEntityStore;

    @Autowired
    private SellerValidationOrchestrator validationOrchestrator;

    @Override
    public void validateSellerCreation(CreateSellerRequest request) {
        // Delegate to orchestrator which runs all enabled validators
        validationOrchestrator.validateAndThrow(request);
    }

    @Override
    public boolean existsByEmail(String email) {
        return sellerEntityStore.existsByContactEmail(email);
    }

    @Override
    public boolean existsByTaxId(String taxId) {
        // TODO: Implement based on your TaxInfo structure
        return false;
    }
}
