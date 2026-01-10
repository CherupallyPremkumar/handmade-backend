package com.handmade.ecommerce.order.dispute;

import com.handmade.ecommerce.order.dispute.entity.DisputeResolution;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for DisputeResolution
 * Generated from entity file
 */
@Repository
public interface DisputeResolutionRepository extends JpaRepository<DisputeResolution, String> {
    
    Optional<DisputeResolution> findByDisputeId(String disputeId);
    List<DisputeResolution> findByResolutionType(String resolutionType);
    List<DisputeResolution> findByCurrencyCode(String currencyCode);
    List<DisputeResolution> findByRefundId(String refundId);

    // Existence checks
    boolean existsByDisputeId(String disputeId);
}
