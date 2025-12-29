package com.handmade.ecommerce.orchestration.seller.application.workflow.chains;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

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
public class IdentityVerificationChain extends Chain<SellerOnboardingContext> {

    public IdentityVerificationChain() {
        super();
    }
}
