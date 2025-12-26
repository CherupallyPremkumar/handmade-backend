package com.handmade.ecommerce.orchestrator.seller.chains;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

import java.util.Map;

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
public class TaxVerificationChain extends Chain<Map<String, Object>> {
    
    public TaxVerificationChain() {
        super();
    }
}
