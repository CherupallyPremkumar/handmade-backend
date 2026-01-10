package com.handmade.ecommerce.order.dispute;

import com.handmade.ecommerce.order.dispute.entity.DisputeEvidence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for DisputeEvidence
 * Generated from entity file
 */
@Repository
public interface DisputeEvidenceRepository extends JpaRepository<DisputeEvidence, String> {
    
    List<DisputeEvidence> findByDisputeId(String disputeId);
    List<DisputeEvidence> findByEvidenceType(String evidenceType);
}
