package com.handmade.ecommerce.seller.account.application.activation;

import com.handmade.ecommerce.identity.event.OnboardingCompletedEvent;
import com.handmade.ecommerce.seller.account.event.*;

public interface ActivationOrchestrator {
    void onOnboardingCompleted(OnboardingCompletedEvent event);

    void onSellerCreated(SellerCreatedEvent event);

    void onStoreCreated(StoreCreatedEvent event);

    void onPayoutProfileInitialized(PayoutProfileInitializedEvent event);

    void onComplianceSnapshotCreated(ComplianceSnapshotCreatedEvent event);
}
