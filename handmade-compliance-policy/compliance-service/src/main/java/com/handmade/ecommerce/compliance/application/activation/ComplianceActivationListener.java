package com.handmade.ecommerce.compliance.application.activation;

import com.handmade.ecommerce.compliance.domain.entity.ComplianceSnapshot;
import com.handmade.ecommerce.compliance.repository.ComplianceSnapshotRepository;
import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.orchestration.seller.command.CreateComplianceSnapshotCommand;
import com.handmade.ecommerce.orchestration.seller.event.ComplianceSnapshotCreatedEvent;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Listener for Compliance Activation commands.
 * Handles CREATE_COMPLIANCE_SNAPSHOT command emitted by the orchestrator.
 */
@Service("complianceActivationListener")
@ChenileController(value = "complianceActivationListener", serviceName = "complianceActivationListener")
public class ComplianceActivationListener {

    @Autowired
    private ComplianceSnapshotRepository complianceSnapshotRepository;

    @Autowired
    private EventPublisher eventPublisher;

    /**
     * Responds to CREATE_COMPLIANCE_SNAPSHOT command.
     * Captures a point-in-time compliance snapshot for the new seller.
     */
    @EventsSubscribedTo({ "CREATE_COMPLIANCE_SNAPSHOT" })
    public void onCreateComplianceSnapshot(CreateComplianceSnapshotCommand command) {
        ComplianceSnapshot snapshot = new ComplianceSnapshot();
        snapshot.setSellerId(command.getSellerId());
        snapshot.setSnapshotDate(LocalDateTime.now());
        snapshot.setStatus("COMPLIANT"); // Initial assumption

        complianceSnapshotRepository.save(snapshot);

        // Notify orchestrator of success
        ComplianceSnapshotCreatedEvent event = new ComplianceSnapshotCreatedEvent(
                command.getWorkflowId(),
                command.getSellerId());
        eventPublisher.publish("COMPLIANCE_SNAPSHOT_CREATED", event);
    }
}
