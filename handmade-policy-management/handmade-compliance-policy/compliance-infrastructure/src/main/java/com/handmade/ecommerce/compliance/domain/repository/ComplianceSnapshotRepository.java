package com.handmade.ecommerce.compliance.domain;

import com.handmade.ecommerce.compliance.domain.entity.ComplianceSnapshot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ComplianceSnapshot
 * Generated from entity file
 */
@Repository
public interface ComplianceSnapshotRepository extends JpaRepository<ComplianceSnapshot, String> {
    
    List<ComplianceSnapshot> findBySellerId(String sellerId);
    List<ComplianceSnapshot> findByPolicyId(String policyId);
    List<ComplianceSnapshot> findByStatus(String status);
}
