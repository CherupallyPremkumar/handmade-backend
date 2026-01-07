package com.handmade.ecommerce.seller.account.application.workflow.chains;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;
import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

/**
 * Chain: Start Onboarding
 * Orchestrates the transition from DRAFT to ONBOARDING state.
 */
@Component("start-onboarding-chain")
public class StartOnboardingChain extends Chain<SellerOnboardingContext> {

    public StartOnboardingChain() {
        super();
    }
}
