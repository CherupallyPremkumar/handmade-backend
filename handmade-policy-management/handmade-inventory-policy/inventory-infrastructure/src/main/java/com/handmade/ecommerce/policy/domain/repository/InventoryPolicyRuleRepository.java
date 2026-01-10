package com.handmade.ecommerce.policy.domain;

import com.handmade.ecommerce.policy.domain.entity.InventoryPolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for InventoryPolicyRule
 * Generated from entity file
 */
@Repository
public interface InventoryPolicyRuleRepository extends JpaRepository<InventoryPolicyRule, String> {
    
    List<InventoryPolicyRule> findByRuleName(String ruleName);
    List<InventoryPolicyRule> findByViolationType(InventoryViolationType violationType);
}
