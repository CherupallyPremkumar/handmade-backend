package com.handmade.ecommerce.seller.account.application.workflow.routers;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;

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
