package com.handmade.ecommerce.policy.domain;

import com.handmade.ecommerce.policy.domain.entity.FraudPolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FraudPolicyRule
 * Generated from entity file
 */
@Repository
public interface FraudPolicyRuleRepository extends JpaRepository<FraudPolicyRule, String> {
    
    List<FraudPolicyRule> findByRuleName(String ruleName);
    List<FraudPolicyRule> findByViolationType(FraudViolationType violationType);
}
