package com.handmade.ecommerce.policy.configuration.dao;

import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for OnboardingPolicyRule
 */
@Repository
public interface OnboardingPolicyRuleRepository extends JpaRepository<OnboardingPolicyRule, String> {
    
    /**
     * Find all rules for a policy, ordered by step_order
     */
    List<OnboardingPolicyRule> findByPolicyIdOrderByStepOrder(String policyId);
    
    /**
     * Find a specific rule by policy ID and step name
     */
    Optional<OnboardingPolicyRule> findByPolicyIdAndStepName(String policyId, String stepName);
    
    /**
     * Find all required rules for a policy
     */
    List<OnboardingPolicyRule> findByPolicyIdAndRequiredTrueOrderByStepOrder(String policyId);
    
    /**
     * Count rules for a policy
     */
    long countByPolicyId(String policyId);
}
