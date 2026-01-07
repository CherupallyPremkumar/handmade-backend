package com.handmade.ecommerce.seller.account.application.workflow.chains;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;

import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

/**
 * Chain 5: Bank Verification
 * State Transition: TAX_VERIFICATION → BANK_VERIFICATION
 * 
 * Steps:
 * 1. Route to bank provider (Stripe Connect/Penny Drop/SEPA)
 * 2. Start bank verification
 * 3. Transition to BANK_VERIFICATION state
 */
@Component("bank-verification-chain")
public class BankVerificationChain extends Chain<SellerOnboardingContext> {

    public BankVerificationChain() {
        super();
    }
}
