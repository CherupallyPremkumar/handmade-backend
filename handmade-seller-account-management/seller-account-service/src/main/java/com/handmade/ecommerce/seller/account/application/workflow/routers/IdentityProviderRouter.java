package com.handmade.ecommerce.seller.account.application.workflow.routers;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;

import org.chenile.owiz.impl.ognl.OgnlRouter;
import org.springframework.stereotype.Component;

/**
 * Router for identity provider based on country
 * Spring bean name: identity-provider-router
 */
@Component("identity-provider-router")
public class IdentityProviderRouter extends OgnlRouter<SellerOnboardingContext> {

    public IdentityProviderRouter() {
        // Expression is set via XML: expression="country"
    }
}
