package com.handmade.ecommerce.seller.account.application.activation;

import org.chenile.http.annotation.EventsSubscribedTo;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.service.store.SellerAccountStore;
import com.handmade.ecommerce.seller.account.event.SellerActivatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Listener that closes the Seller Onboarding loopback.
 * Triggered when the orchestrator confirms that all downstream systems
 * (Seller, Store, Payout, Compliance) have been activated.
 */
@Component
public class OnboardingActivationListener {

    @Autowired
    private SellerAccountStore<SellerAccount> onboardingCaseStore;

    @Transactional
    @EventsSubscribedTo({ "SELLER_ACTIVATED" })
    public void onSellerActivated(SellerActivatedEvent event) {
        String onboardingCaseId = event.getSellerId(); // Using Seller ID as Onboarding Case ID

        SellerAccount onboardingCase = onboardingCaseStore.retrieve(onboardingCaseId);
        if (onboardingCase != null) {
            // Add activity log for the final activation confirmation
            onboardingCase.addActivity("SELLER_ACTIVATED",
                    "Seller fully activated across all modules. Workflow ID: " + event.getWorkflowId());

            // Mark as active and completed if it was in PROVISION_PENDING or similar
            // The ProvisionSellerAction already moves it to ACTIVE state in STM
            // but we can add additional metadata here.

            onboardingCaseStore.store(onboardingCase);
        }
    }
}
