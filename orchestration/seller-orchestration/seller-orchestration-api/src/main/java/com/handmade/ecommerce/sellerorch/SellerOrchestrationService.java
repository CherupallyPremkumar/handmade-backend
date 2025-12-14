package com.handmade.ecommerce.sellerorch;

import com.handmade.ecommerce.dto.seller.CreateSellerRequest;
import com.handmade.ecommerce.dto.seller.SellerResponse;

/**
 * Seller Orchestration Service Interface
 * Coordinates seller-related operations across multiple services
 */
public interface SellerOrchestrationService {
    
    /**
     * Onboard a new seller
     * (create seller + setup payment account + create initial products)
     */
    SellerResponse onboardSeller(CreateSellerRequest request);
    
    /**
     * Get seller dashboard data
     * (seller info + products + orders + analytics)
     */
    SellerResponse getSellerDashboard(Long sellerId);
    
    /**
     * Update seller profile and sync across services
     */
    SellerResponse updateSellerProfile(Long sellerId, CreateSellerRequest request);
}
