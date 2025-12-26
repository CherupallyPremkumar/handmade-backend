package com.handmade.ecommerce.orchestrator.seller.chains;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Chain 3: Identity Verification
 * State Transition: ONBOARDING → IDENTITY_VERIFICATION
 * 
 * Steps:
 * 1. Route to identity provider (Stripe/DigiLocker/eIDAS)
 * 2. Start identity verification
 * 3. Transition to IDENTITY_VERIFICATION state
 */
@Component("identity-verification-chain")
public class IdentityVerificationChain extends Chain<Map<String, Object>> {
    
    public IdentityVerificationChain() {
        super();
    }
}
