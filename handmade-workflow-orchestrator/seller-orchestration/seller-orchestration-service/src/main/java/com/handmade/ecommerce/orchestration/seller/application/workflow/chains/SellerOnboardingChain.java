package com.handmade.ecommerce.orchestration.seller.application.workflow.chains;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;
import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

/**
 * Root Chain for Seller Onboarding.
 * Coordinates the overall sequence of onboarding steps.
 */
@Component("seller-onboarding-chain")
public class SellerOnboardingChain extends Chain<SellerOnboardingContext> {

    public SellerOnboardingChain() {
        super();
    }
}
