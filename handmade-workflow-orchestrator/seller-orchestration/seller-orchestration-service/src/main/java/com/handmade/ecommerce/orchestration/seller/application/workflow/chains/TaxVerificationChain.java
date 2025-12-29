package com.handmade.ecommerce.orchestration.seller.application.workflow.chains;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

/**
 * Chain 4: Tax Verification
 * State Transition: IDENTITY_VERIFICATION → TAX_VERIFICATION
 * 
 * Steps:
 * 1. Route to tax provider (Stripe Tax/GST/VAT)
 * 2. Start tax verification
 * 3. Transition to TAX_VERIFICATION state
 */
@Component("tax-verification-chain")
public class TaxVerificationChain extends Chain<SellerOnboardingContext> {

    public TaxVerificationChain() {
        super();
    }
}
