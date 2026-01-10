package com.handmade.ecommerce.platform.policy;

import com.handmade.ecommerce.platform.policy.entity.PolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PolicyRule
 * Generated from entity file
 */
@Repository
public interface PolicyRuleRepository extends JpaRepository<PolicyRule, String> {
    
    List<PolicyRule> findByPolicyDefinitionId(String policyDefinitionId);
    List<PolicyRule> findByRuleName(String ruleName);
    List<PolicyRule> findByPriority(String priority);
    List<PolicyRule> findByIsActive(Boolean isActive);
}
