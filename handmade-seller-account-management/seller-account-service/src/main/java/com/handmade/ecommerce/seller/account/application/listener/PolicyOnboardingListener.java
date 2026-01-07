package com.handmade.ecommerce.seller.account.application.listener;

import com.handmade.ecommerce.policy.event.PolicyAcceptedEvent;
import com.handmade.ecommerce.seller.account.api.SellerAccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.LockPolicyRequest;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener that reacts to policy acceptance signals to drive the onboarding
 * process.
 */
@Component
public class PolicyOnboardingListener {

    private final Logger logger = LoggerFactory.getLogger(PolicyOnboardingListener.class);

    @Autowired
    @Qualifier("sellerAccountService")
    private SellerAccountService sellerAccountService;

    /**
     * Reacts to the POLICY_ACCEPTED event.
     * Maps the signal to the 'lockPolicy' event in the Onboarding STM.
     */
    @EventsSubscribedTo("POLICY_ACCEPTED")
    public void onPolicyAccepted(PolicyAcceptedEvent event) {
        logger.info("Received policy acceptance for seller: {}, case: {}",
                event.getSellerId(), event.getOnboardingCaseId());

        try {
            LockPolicyRequest lockPolicyRequest = new LockPolicyRequest();
            lockPolicyRequest.setPolicyId(event.getPolicyId());
            // Need a way to get policy version if not in event.
            // For now, setting it to dummy if not present or updating event.

            // Trigger the STM transition to move from DRAFT to POLICY_LOCKED
            sellerAccountService.lockPolicy(event.getOnboardingCaseId(), lockPolicyRequest);
            logger.info("Successfully locked policy for onboarding case: {}", event.getOnboardingCaseId());
        } catch (Exception e) {
            logger.error("Failed to transition onboarding state for case: {}", event.getOnboardingCaseId(), e);
        }
    }
}
