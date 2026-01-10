package com.handmade.ecommerce.compliance.domain;

import com.handmade.ecommerce.compliance.domain.entity.CompliancePolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CompliancePolicyRule
 * Generated from entity file
 */
@Repository
public interface CompliancePolicyRuleRepository extends JpaRepository<CompliancePolicyRule, String> {
    
    List<CompliancePolicyRule> findByPolicyId(String policyId);
    List<CompliancePolicyRule> findByRuleName(String ruleName);
    List<CompliancePolicyRule> findByProviderName(String providerName);
}
