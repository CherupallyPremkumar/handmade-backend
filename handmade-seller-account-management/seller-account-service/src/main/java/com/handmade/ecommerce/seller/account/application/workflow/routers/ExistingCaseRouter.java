package com.handmade.ecommerce.seller.account.application.workflow.routers;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;
import org.chenile.owiz.impl.ognl.OgnlRouter;
import org.springframework.stereotype.Component;

/**
 * Router to decide whether to return an existing case or create a new one.
 * Uses the 'caseExists' flag from the SellerOnboardingContext.
 */
@Component("existing-case-router")
public class ExistingCaseRouter extends OgnlRouter<SellerOnboardingContext> {

    public ExistingCaseRouter() {
        // expression="caseExists" is usually set in XML or can be set here
    }
}
