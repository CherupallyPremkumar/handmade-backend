package com.handmade.ecommerce.platform.policy;

import com.handmade.ecommerce.platform.policy.entity.PolicyDecision;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PolicyDecision
 * Generated from entity file
 */
@Repository
public interface PolicyDecisionRepository extends JpaRepository<PolicyDecision, String> {
    
    Optional<PolicyDecision> findByTraceId(String traceId);
    List<PolicyDecision> findByPolicyKey(String policyKey);

    // Existence checks
    boolean existsByTraceId(String traceId);
}
