package com.handmade.ecommerce.policy.service.store;

import com.handmade.ecommerce.policy.configuration.dao.PricingPolicyRepository;
import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class PricingPolicyEntityStore implements EntityStore<PricingPolicy> {

    @Autowired
    private PricingPolicyRepository repository;

    @Override
    public PricingPolicy retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(PricingPolicy entity) {
        repository.save(entity);
    }

    public Optional<PricingPolicy> findActivePolicy(String country, String category, LocalDate date) {
        return repository.findActivePolicy(country, category, date);
    }

    public boolean hasActivePolicy(String country, String category, LocalDate date) {
        return repository.findActivePolicy(country, category, date).isPresent();
    }

    public List<PricingPolicy> findAllActivePolicies(LocalDate date) {
        return repository.findAllActivePolicies(date);
    }

    public List<PricingPolicy> findAllDraftPolicies() {
        return repository.findAllDraftPolicies();
    }

    public Optional<PricingPolicy> findByPolicyVersion(String version) {
        return repository.findByPolicyVersion(version);
    }
}
