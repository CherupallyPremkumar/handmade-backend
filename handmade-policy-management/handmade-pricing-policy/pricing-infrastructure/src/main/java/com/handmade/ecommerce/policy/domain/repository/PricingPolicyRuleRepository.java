package com.handmade.ecommerce.policy.domain;

import com.handmade.ecommerce.policy.domain.entity.PricingPolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PricingPolicyRule
 * Generated from entity file
 */
@Repository
public interface PricingPolicyRuleRepository extends JpaRepository<PricingPolicyRule, String> {
    
    List<PricingPolicyRule> findByPolicyId(String policyId);
    List<PricingPolicyRule> findByRuleName(String ruleName);
    List<PricingPolicyRule> findByViolationType(PricingViolationType violationType);
}
