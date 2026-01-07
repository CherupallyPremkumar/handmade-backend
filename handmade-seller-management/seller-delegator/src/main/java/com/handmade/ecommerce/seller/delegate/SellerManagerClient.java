package com.handmade.ecommerce.seller.delegate;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;

/**
 * Business Delegate for Seller Management
 * Allows remote calls to the Seller Manager service
 * Following the same delegation pattern as Platform Management
 * 
 * Handles Seller (store) operations
 */
public interface SellerManagerClient {

    // ===== Generic Event Processing =====

    /**
     * Process any state machine event for Seller store
     */
    Seller processSellerEvent(String sellerId, String event, Object payload);

    // ===== Query Operations =====

    /**
     * Retrieve seller store by ID
     */
    Seller getSeller(String sellerId);

    /**
     * Check if email exists in operational context
     */
    boolean existsByEmail(String email);
}
