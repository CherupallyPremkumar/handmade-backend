package com.handmade.ecommerce.orchestrator.seller.routers;

import org.chenile.owiz.impl.ognl.OgnlRouter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Router for identity verification based on identityRequired flag
 * Spring bean name: identity-verification-router
 */
@Component("identity-verification-router")
public class IdentityVerificationRouter extends OgnlRouter<Map<String, Object>> {
    
    public IdentityVerificationRouter() {
        // Expression is set via XML: expression="identityRequired"
    }
}
