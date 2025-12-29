package com.handmade.ecommerce.seller.dto.command;

import com.handmade.ecommerce.platform.domain.enums.SellerType;
import lombok.Data;

import java.io.Serializable;

/**
 * Payload for initializing seller onboarding
 * 
 * Week 1: Policy-versioned onboarding
 */
@Data
public class InitializeOnboardingPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Seller email (unique identifier)
     */
    private String email;
    
    /**
     * Business/display name
     */
    private String businessName;
    
    /**
     * ISO 3166-1 alpha-2 country code
     */
    private String countryCode;
    
    /**
     * Seller type (determines policy)
     */
    private SellerType sellerType;
    
    /**
     * Platform ID this seller belongs to
     */
    private String platformId;
}
