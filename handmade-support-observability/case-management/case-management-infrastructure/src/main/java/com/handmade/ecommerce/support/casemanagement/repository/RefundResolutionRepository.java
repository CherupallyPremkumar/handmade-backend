package com.handmade.ecommerce.support.casemanagement;

import com.handmade.ecommerce.support.casemanagement.entity.RefundResolution;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for RefundResolution
 * Generated from entity file
 */
@Repository
public interface RefundResolutionRepository extends JpaRepository<RefundResolution, String> {
    
    Optional<RefundResolution> findByClaimId(String claimId);
    List<RefundResolution> findByOrderId(String orderId);
    List<RefundResolution> findByClaimStatus(String claimStatus);

    // Existence checks
    boolean existsByClaimId(String claimId);
}
