package com.handmade.ecommerce.policy.service.store;

import com.handmade.ecommerce.policy.configuration.dao.ListingPolicyRepository;
import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ListingPolicyEntityStore implements EntityStore<ListingPolicy> {

    @Autowired
    private ListingPolicyRepository repository;

    @Override
    public ListingPolicy retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(ListingPolicy entity) {
        repository.save(entity);
    }

    public Optional<ListingPolicy> findActivePolicy(String country, String category, LocalDate date) {
        return repository.findActivePolicy(country, category, date);
    }

    public List<ListingPolicy> findAllActivePolicies(LocalDate date) {
        return repository.findAllActivePolicies(date);
    }

    public List<ListingPolicy> findAllDraftPolicies() {
        return repository.findAllDraftPolicies();
    }

    public Optional<ListingPolicy> findByPolicyVersion(String version) {
        return repository.findByPolicyVersion(version);
    }
}
