package com.handmade.ecommerce.seller.api;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import org.chenile.workflow.api.StateEntityService;

/**
 * Service interface for SellerAccount aggregate
 * Handles account lifecycle: registration, verification, activation, suspension
 */
public interface SellerAccountService extends StateEntityService<SellerAccount> {
    // Inherits methods from StateEntityService:
    // - create(SellerAccount)
    // - processById(String id, String eventId, Object payload)
    // - retrieve(String id)
}
