package com.handmade.ecommerce.policy.service.impl;

import com.handmade.ecommerce.policy.api.OnboardingPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.policy.configuration.dao.OnboardingPolicyRepository;
import com.handmade.ecommerce.policy.service.store.OnboardingPolicyEntityStore;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of OnboardingPolicyManager using Chenile's generic StateEntityService.
 */
public class OnboardingPolicyManagerImpl extends StateEntityServiceImpl<OnboardingPolicy> implements OnboardingPolicyManager {

    private OnboardingPolicyEntityStore onboardingPolicyEntityStore;

    public OnboardingPolicyManagerImpl(STM<OnboardingPolicy> stm, STMActionsInfoProvider infoProvider, 
            EntityStore<OnboardingPolicy> entityStore) {
        super(stm, infoProvider, entityStore);
        this.onboardingPolicyEntityStore = (OnboardingPolicyEntityStore) entityStore;
    }

    @Override
    public OnboardingPolicy resolveActivePolicy(String country, SellerType sellerType, LocalDate date) {
        return onboardingPolicyEntityStore.findActivePolicy(country, sellerType, date).orElse(null);
    }

    @Override
    public OnboardingPolicy getPolicyByVersion(String version) {
        return onboardingPolicyEntityStore.findByPolicyVersion(version).orElse(null);
    }

    @Override
    public boolean canOnboardInCountry(String country, SellerType sellerType) {
        return onboardingPolicyEntityStore.hasActivePolicy(country, sellerType, LocalDate.now());
    }

    @Override
    public List<OnboardingPolicy> getAllActivePolicies() {
        return onboardingPolicyEntityStore.findAllActivePolicies(LocalDate.now());
    }

    @Override
    public List<OnboardingPolicy> getAllDraftPolicies() {
        return onboardingPolicyEntityStore.findAllDraftPolicies();
    }

    @Override
    public Optional<OnboardingPolicyRule> getRuleForStep(String policyId, String stepName) {
        if (policyId == null || stepName == null) {
            return Optional.empty();
        }
        
        OnboardingPolicy policy = getPolicyByVersion(policyId);
        if (policy == null) return Optional.empty();
        
        return policy.getRules().stream()
                .filter(rule -> stepName.equals(rule.getStepName()))
                .findFirst();
    }
}
