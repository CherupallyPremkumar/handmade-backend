package com.handmade.ecommerce.payout.domain;

import com.handmade.ecommerce.payout.domain.entity.PayoutPolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PayoutPolicyRule
 * Generated from entity file
 */
@Repository
public interface PayoutPolicyRuleRepository extends JpaRepository<PayoutPolicyRule, String> {
    
    List<PayoutPolicyRule> findByPolicyId(String policyId);
    List<PayoutPolicyRule> findByRuleName(String ruleName);
}
