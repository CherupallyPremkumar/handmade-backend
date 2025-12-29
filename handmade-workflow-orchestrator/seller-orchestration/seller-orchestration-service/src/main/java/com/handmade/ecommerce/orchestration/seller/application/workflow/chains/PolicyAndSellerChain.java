package com.handmade.ecommerce.orchestration.seller.application.workflow.chains;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

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
public class PolicyAndSellerChain extends Chain<SellerOnboardingContext> {

    public PolicyAndSellerChain() {
        super();
    }
}
