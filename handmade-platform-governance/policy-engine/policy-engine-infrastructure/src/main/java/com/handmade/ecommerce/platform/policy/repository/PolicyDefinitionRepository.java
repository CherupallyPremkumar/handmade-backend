package com.handmade.ecommerce.platform.policy;

import com.handmade.ecommerce.platform.policy.entity.PolicyDefinition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PolicyDefinition
 * Generated from entity file
 */
@Repository
public interface PolicyDefinitionRepository extends JpaRepository<PolicyDefinition, String> {
    
    Optional<PolicyDefinition> findByPolicyKey(String policyKey);
    List<PolicyDefinition> findByPolicyName(String policyName);
    List<PolicyDefinition> findByIsActive(Boolean isActive);

    // Existence checks
    boolean existsByPolicyKey(String policyKey);
}
