package com.handmade.ecommerce.orchestrator.seller.chains;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Main chain for seller onboarding workflow
 * Spring bean name: seller-onboarding-chain
 */
@Component("seller-onboarding-chain")
public class SellerOnboardingChain extends Chain<Map<String, Object>> {
    
    public SellerOnboardingChain() {
        super();
    }
}
