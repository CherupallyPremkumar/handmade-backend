package com.handmade.ecommerce.orchestrator.seller.chains;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Chain 2: Start Onboarding
 * State Transition: DRAFT → ONBOARDING
 * 
 * Steps:
 * 1. Transition seller to ONBOARDING state
 */
@Component("start-onboarding-chain")
public class StartOnboardingChain extends Chain<Map<String, Object>> {
    
    public StartOnboardingChain() {
        super();
    }
}
