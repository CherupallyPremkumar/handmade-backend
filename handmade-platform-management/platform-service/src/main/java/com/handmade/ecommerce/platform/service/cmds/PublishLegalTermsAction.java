package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.PublishLegalTermsPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action to publish legal terms and conditions for the platform
 */
public class PublishLegalTermsAction extends AbstractSTMTransitionAction<PlatformOwner, PublishLegalTermsPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(PublishLegalTermsAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                            PublishLegalTermsPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Publishing legal terms for platform: {}", platform.id);
        
        // TODO: Store legal terms in platform.legalTerms value object once created
        logger.info("Terms of Service URL: {}", payload.getTermsOfServiceUrl());
        logger.info("Privacy Policy URL: {}", payload.getPrivacyPolicyUrl());
        logger.info("Refund Policy URL: {}", payload.getRefundPolicyUrl());
        logger.info("Effective Date: {}", payload.getEffectiveDate());
        logger.info("Version: {}", payload.getVersion());
        
        logger.info("Legal terms published for platform {}", platform.id);
    }
}
