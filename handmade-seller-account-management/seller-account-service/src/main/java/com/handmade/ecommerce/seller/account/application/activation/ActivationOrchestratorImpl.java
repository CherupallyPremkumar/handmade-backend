package com.handmade.ecommerce.seller.account.application.activation;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.identity.event.OnboardingCompletedEvent;
import com.handmade.ecommerce.seller.account.command.*;
import com.handmade.ecommerce.seller.account.event.*;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("activationOrchestrator")
@ChenileController(value = "activationOrchestrator", serviceName = "activationOrchestrator")
public class ActivationOrchestratorImpl implements ActivationOrchestrator {

    @Autowired
    private EventPublisher eventPublisher;

    @Override
    @EventsSubscribedTo({ "ONBOARDING_COMPLETED" })
    public void onOnboardingCompleted(OnboardingCompletedEvent event) {
        String workflowId = UUID.randomUUID().toString();
        CreateSellerCommand command = new CreateSellerCommand(
                workflowId,
                event.getOnboardingCaseId(),
                event.getEmail(),
                event.getCountry());
        eventPublisher.publish("CREATE_SELLER", command);
    }

    @Override
    @EventsSubscribedTo({ "SELLER_CREATED" })
    public void onSellerCreated(SellerCreatedEvent event) {
        CreateStoreCommand command = new CreateStoreCommand(
                event.getWorkflowId(),
                event.getSellerId());
        eventPublisher.publish("CREATE_STORE", command);
    }

    @Override
    @EventsSubscribedTo({ "STORE_CREATED" })
    public void onStoreCreated(StoreCreatedEvent event) {
        InitPayoutProfileCommand command = new InitPayoutProfileCommand(
                event.getWorkflowId(),
                event.getSellerId());
        eventPublisher.publish("INIT_PAYOUT_PROFILE", command);
    }

    @Override
    @EventsSubscribedTo({ "PAYOUT_PROFILE_INITIALIZED" })
    public void onPayoutProfileInitialized(PayoutProfileInitializedEvent event) {
        CreateComplianceSnapshotCommand command = new CreateComplianceSnapshotCommand(
                event.getWorkflowId(),
                event.getSellerId());
        eventPublisher.publish("CREATE_COMPLIANCE_SNAPSHOT", command);
    }

    @Override
    @EventsSubscribedTo({ "COMPLIANCE_SNAPSHOT_CREATED" })
    public void onComplianceSnapshotCreated(ComplianceSnapshotCreatedEvent event) {
        SellerActivatedEvent activatedEvent = new SellerActivatedEvent(
                event.getWorkflowId(),
                event.getSellerId());
        eventPublisher.publish("SELLER_ACTIVATED", activatedEvent);
    }
}
