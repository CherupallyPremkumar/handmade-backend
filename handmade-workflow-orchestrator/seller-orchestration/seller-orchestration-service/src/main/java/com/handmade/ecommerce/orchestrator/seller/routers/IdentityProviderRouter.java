package com.handmade.ecommerce.orchestrator.seller.routers;

import org.chenile.owiz.impl.ognl.OgnlRouter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Router for identity provider based on country
 * Spring bean name: identity-provider-router
 */
@Component("identity-provider-router")
public class IdentityProviderRouter extends OgnlRouter<Map<String, Object>> {
    
    public IdentityProviderRouter() {
        // Expression is set via XML: expression="country"
    }
}
