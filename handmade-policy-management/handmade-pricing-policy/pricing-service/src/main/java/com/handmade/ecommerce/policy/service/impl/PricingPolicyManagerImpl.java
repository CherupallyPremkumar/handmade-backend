package com.handmade.ecommerce.policy.service.impl;

import com.handmade.ecommerce.policy.api.PricingPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.domain.entity.PricingPolicyRule;
import com.handmade.ecommerce.policy.domain.valueobject.PricingDecision;
import com.handmade.ecommerce.policy.service.store.PricingPolicyEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PricingPolicyManagerImpl extends StateEntityServiceImpl<PricingPolicy> implements PricingPolicyManager {

    private PricingPolicyEntityStore pricingPolicyEntityStore;

    public PricingPolicyManagerImpl(STM<PricingPolicy> stm, STMActionsInfoProvider infoProvider, 
            EntityStore<PricingPolicy> entityStore) {
        super(stm, infoProvider, entityStore);
        this.pricingPolicyEntityStore = (PricingPolicyEntityStore) entityStore;
    }

    @Override
    public PricingPolicy resolveActivePolicy(String country, String category, LocalDate date) {
        return pricingPolicyEntityStore.findActivePolicy(country, category, date).orElse(null);
    }

    @Override
    public PricingPolicy getPolicyByVersion(String version) {
        return pricingPolicyEntityStore.findByPolicyVersion(version).orElse(null);
    }

    @Override
    public boolean canPriceInCountry(String country, String category) {
        return pricingPolicyEntityStore.hasActivePolicy(country, category, LocalDate.now());
    }

    @Override
    public List<PricingPolicy> getAllActivePolicies() {
        return pricingPolicyEntityStore.findAllActivePolicies(LocalDate.now());
    }

    @Override
    public List<PricingPolicy> getAllDraftPolicies() {
        return pricingPolicyEntityStore.findAllDraftPolicies();
    }

    @Override
    public Optional<PricingPolicyRule> getRuleForCheck(String policyId, String ruleName) {
        if (policyId == null || ruleName == null) {
            return Optional.empty();
        }
        
        PricingPolicy policy = getPolicyByVersion(policyId);
        if (policy == null) return Optional.empty();
        
        return policy.getRules().stream()
                .filter(rule -> ruleName.equals(rule.getRuleName()))
                .findFirst();
    }

    @Override
    public PricingDecision validatePrice(String country, String category, Long priceCents, 
                                          String productId, String sellerId) {
        PricingPolicy policy = resolveActivePolicy(country, category, LocalDate.now());
        if (policy == null) {
            return PricingDecision.APPROVED;
        }
        
        if (policy.getMinPriceCents() != null && priceCents < policy.getMinPriceCents()) {
            return PricingDecision.REJECTED;
        }
        if (policy.getMaxPriceCents() != null && priceCents > policy.getMaxPriceCents()) {
            return PricingDecision.REJECTED;
        }
        
        if (policy.getManualReviewThresholdCents() != null && 
            priceCents > policy.getManualReviewThresholdCents()) {
            return PricingDecision.REQUIRES_REVIEW;
        }
        
        return PricingDecision.APPROVED;
    }

    @Override
    public PricingDecision checkPriceChange(String country, String category, 
                                             Long oldPriceCents, Long newPriceCents,
                                             String productId, String sellerId) {
        PricingPolicy policy = resolveActivePolicy(country, category, LocalDate.now());
        if (policy == null) {
            return PricingDecision.APPROVED;
        }
        
        if (oldPriceCents != null && oldPriceCents > 0) {
            long changePercentage = ((newPriceCents - oldPriceCents) * 100) / oldPriceCents;
            
            if (policy.isPriceGougingDetectionEnabled() && changePercentage > 100) {
                return PricingDecision.REQUIRES_REVIEW;
            }
        }
        
        return validatePrice(country, category, newPriceCents, productId, sellerId);
    }
}
