package com.handmade.ecommerce.platform.service.policy;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.platform.domain.policy.OnboardingPolicyRule;
import com.handmade.ecommerce.platform.repository.OnboardingPolicyRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for accessing onboarding policy rules
 * 
 * Week 1: Fetch rules for a seller's locked policy
 */
@Service
public class OnboardingPolicyService {
    
    private static final Logger logger = LoggerFactory.getLogger(OnboardingPolicyService.class);
    
    @Autowired
    private OnboardingPolicyRuleRepository ruleRepository;
    
    /**
     * Get all onboarding rules for a seller's locked policy
     * 
     * @param seller Seller account
     * @return List of rules, ordered by step_order
     */
    public List<OnboardingPolicyRule> getRulesForSeller(SellerAccount seller) {
        
        String policyId = seller.getOnboardingPolicyId();
        
        if (policyId == null) {
            throw new IllegalStateException(
                "Seller " + seller.getId() + " has no locked policy"
            );
        }
        
        List<OnboardingPolicyRule> rules = ruleRepository.findByPolicyIdOrderByStepOrder(policyId);
        
        logger.info("Loaded {} rules for seller {} (policy version {})",
                   rules.size(), seller.getId(), seller.getOnboardingPolicyVersion());
        
        return rules;
    }
    
    /**
     * Get a specific rule by step name for a seller
     * 
     * @param seller Seller account
     * @param stepName Step name (e.g., "identity_verification")
     * @return Rule if exists
     */
    public Optional<OnboardingPolicyRule> getRuleForStep(SellerAccount seller, String stepName) {
        
        String policyId = seller.getOnboardingPolicyId();
        
        if (policyId == null) {
            throw new IllegalStateException(
                "Seller " + seller.getId() + " has no locked policy"
            );
        }
        
        return ruleRepository.findByPolicyIdAndStepName(policyId, stepName);
    }
    
    /**
     * Get all required rules for a seller
     */
    public List<OnboardingPolicyRule> getRequiredRulesForSeller(SellerAccount seller) {
        
        String policyId = seller.getOnboardingPolicyId();
        
        if (policyId == null) {
            throw new IllegalStateException(
                "Seller " + seller.getId() + " has no locked policy"
            );
        }
        
        return ruleRepository.findByPolicyIdAndRequiredTrueOrderByStepOrder(policyId);
    }
}
