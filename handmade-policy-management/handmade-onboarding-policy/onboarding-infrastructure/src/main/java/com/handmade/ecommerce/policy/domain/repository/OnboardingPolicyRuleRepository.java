package com.handmade.ecommerce.policy.domain;

import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OnboardingPolicyRule
 * Generated from entity file
 */
@Repository
public interface OnboardingPolicyRuleRepository extends JpaRepository<OnboardingPolicyRule, String> {
    
    List<OnboardingPolicyRule> findByPolicyId(String policyId);
    List<OnboardingPolicyRule> findByStepName(String stepName);
    List<OnboardingPolicyRule> findByProviderType(String providerType);
}
