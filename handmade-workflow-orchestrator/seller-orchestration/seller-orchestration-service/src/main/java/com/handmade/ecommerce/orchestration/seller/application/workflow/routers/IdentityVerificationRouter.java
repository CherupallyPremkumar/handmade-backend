package com.handmade.ecommerce.orchestration.seller.application.workflow.routers;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;

import org.chenile.owiz.impl.ognl.OgnlRouter;
import org.springframework.stereotype.Component;

/**
 * Router for identity verification based on identityRequired flag
 * Spring bean name: identity-verification-router
 */
@Component("identity-verification-router")
public class IdentityVerificationRouter extends OgnlRouter<SellerOnboardingContext> {

    public IdentityVerificationRouter() {
        // Expression is set via XML: expression="identityRequired"
    }
}
