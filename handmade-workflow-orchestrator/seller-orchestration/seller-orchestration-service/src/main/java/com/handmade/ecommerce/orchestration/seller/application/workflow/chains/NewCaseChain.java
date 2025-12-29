package com.handmade.ecommerce.orchestration.seller.application.workflow.chains;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;
import org.chenile.owiz.impl.Chain;
import org.springframework.stereotype.Component;

/**
 * Chain to handle the creation and setup of a new onboarding case.
 */
@Component("new-case-chain")
public class NewCaseChain extends Chain<SellerOnboardingContext> {

    public NewCaseChain() {
        super();
    }
}
