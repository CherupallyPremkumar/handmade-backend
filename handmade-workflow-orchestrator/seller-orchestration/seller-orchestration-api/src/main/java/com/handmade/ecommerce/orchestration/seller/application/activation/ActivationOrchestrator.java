package com.handmade.ecommerce.orchestration.seller.application.activation;

import com.handmade.ecommerce.identity.event.OnboardingCompletedEvent;
import com.handmade.ecommerce.orchestration.seller.event.*;

public interface ActivationOrchestrator {
    void onOnboardingCompleted(OnboardingCompletedEvent event);

    void onSellerCreated(SellerCreatedEvent event);

    void onStoreCreated(StoreCreatedEvent event);

    void onPayoutProfileInitialized(PayoutProfileInitializedEvent event);

    void onComplianceSnapshotCreated(ComplianceSnapshotCreatedEvent event);
}
