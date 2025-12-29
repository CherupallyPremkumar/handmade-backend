package com.handmade.ecommerce.policy.service.store;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.repository.OnboardingPolicyRepository;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * JPA-backed entity store for OnboardingPolicy.
 */
public class OnboardingPolicyEntityStore implements EntityStore<OnboardingPolicy> {
    
    @Autowired
    private OnboardingPolicyRepository policyRepository;
    
    @Override
    public void store(OnboardingPolicy entity) {
        policyRepository.save(entity);
    }

    @Override
    public OnboardingPolicy retrieve(String id) {
        return policyRepository.findById(id).orElse(null);
    }

    public Optional<OnboardingPolicy> findActivePolicy(String countryCode, SellerType sellerType, LocalDate today) {
        return policyRepository.findActivePolicy(countryCode, sellerType, today);
    }

    public Optional<OnboardingPolicy> findByPolicyVersion(String policyVersion) {
        return policyRepository.findByPolicyVersion(policyVersion);
    }

    public boolean hasActivePolicy(String countryCode, SellerType sellerType, LocalDate today) {
        return policyRepository.hasActivePolicy(countryCode, sellerType, today);
    }

    public List<OnboardingPolicy> findAllActivePolicies(LocalDate today) {
        return policyRepository.findAllActivePolicies(today);
    }

    public List<OnboardingPolicy> findAllDraftPolicies() {
        return policyRepository.findAllDraftPolicies();
    }
}
