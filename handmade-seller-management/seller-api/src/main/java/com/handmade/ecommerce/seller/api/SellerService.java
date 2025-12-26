package com.handmade.ecommerce.seller.api;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import org.chenile.workflow.api.StateEntityService;

/**
 * Service interface for Seller aggregate
 * Handles store operations: setup, product approval, vacation mode, restrictions
 */
public interface SellerService extends StateEntityService<Seller> {
    // Inherits methods from StateEntityService:
    // - create(Seller)
    // - processById(String id, String eventId, Object payload)
    // - retrieve(String id)
}
