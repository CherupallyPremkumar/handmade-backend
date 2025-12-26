package com.handmade.ecommerce.orchestrator.seller.chains;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Chain 1: Policy Resolution & Seller Creation
 * State: DRAFT
 * 
 * Steps:
 * 1. Resolve onboarding policy
 * 2. Create seller
 * 3. Lock policy version
 */
@Component("policy-and-seller-chain")
public class PolicyAndSellerChain extends Chain<Map<String, Object>> {
    
    public PolicyAndSellerChain() {
        super();
    }
}
