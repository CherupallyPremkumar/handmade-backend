package com.handmade.ecommerce.seller.account.async.listener;

import com.handmade.ecommerce.seller.account.api.SellerAccountService;
import com.handmade.ecommerce.seller.onboarding.api.event.OnboardingApprovedEvent;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Asynchronous listener for account provisioning.
 * This module acts as the entry point for events and handles retry logic for In-VM environments.
 */
@Component
public class AccountProvisioningListener {

    private final Logger logger = LoggerFactory.getLogger(AccountProvisioningListener.class);

    @Value("${handmade.ev:true}")
    private boolean inVM;

    @Autowired
    private SellerAccountService sellerAccountService;

    private final int maxRetries = 3;
    private final long retryDelayMs = 1000;

    @EventsSubscribedTo("OnboardingApprovedEvent")
    public void onOnboardingApproved(OnboardingApprovedEvent event) {
        logger.info("Received OnboardingApprovedEvent for caseId: {}", event.getCaseId());

        if (!inVM) {
            // Real async broker handles retries
            sellerAccountService.createSellerAccountIfNotExists(event);
            return;
        }

        // In-VM retries
        int attempts = 0;
        while (attempts < maxRetries) {
            try {
                sellerAccountService.createSellerAccountIfNotExists(event);
                logger.info("Seller account created successfully for caseId: {}", event.getCaseId());
                return;
            } catch (Exception e) {
                attempts++;
                logger.warn("Retry {}/{} failed for caseId: {}", attempts, maxRetries, event.getCaseId(), e.getMessage());
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Retry interrupted for caseId: {}", event.getCaseId(), ie);
                    break;
                }
            }
        }

        logger.error("Provisioning ultimately failed for caseId: {} after {} attempts", event.getCaseId(), maxRetries);
    }
}