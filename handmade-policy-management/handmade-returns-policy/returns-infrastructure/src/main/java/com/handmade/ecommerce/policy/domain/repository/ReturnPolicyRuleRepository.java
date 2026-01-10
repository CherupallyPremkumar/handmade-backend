package com.handmade.ecommerce.policy.domain;

import com.handmade.ecommerce.policy.domain.entity.ReturnPolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ReturnPolicyRule
 * Generated from entity file
 */
@Repository
public interface ReturnPolicyRuleRepository extends JpaRepository<ReturnPolicyRule, String> {
    
    List<ReturnPolicyRule> findByRuleName(String ruleName);
    List<ReturnPolicyRule> findByViolationType(ReturnViolationType violationType);
}
