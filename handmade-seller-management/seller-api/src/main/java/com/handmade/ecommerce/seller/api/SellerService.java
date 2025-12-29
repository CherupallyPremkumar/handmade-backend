package com.handmade.ecommerce.seller.api;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import org.chenile.workflow.api.StateEntityService;

/**
 * Service interface for Seller aggregate (Operational)
 * Handles storefront lifecycle: Activation, Suspension, Vacation mode
 */
public interface SellerService extends StateEntityService<Seller> {
    /**
     * Check if email exists in operational context
     */
    boolean existsByEmail(String email);
}
