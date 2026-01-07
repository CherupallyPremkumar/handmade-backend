package com.handmade.ecommerce.commission.service.store;

import com.handmade.ecommerce.commission.configuration.dao.CommissionPolicyRepository;
import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class CommissionPolicyEntityStore implements EntityStore<CommissionPolicy> {

    @Autowired
    private CommissionPolicyRepository repository;

    @Override
    public CommissionPolicy retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(CommissionPolicy entity) {
        repository.save(entity);
    }

    public Optional<CommissionPolicy> findActivePolicy(String countryCode, String category, LocalDate date) {
        return repository.findActivePolicy(countryCode, category, date);
    }

    public Optional<CommissionPolicy> findByPolicyVersion(String version) {
        return repository.findByPolicyVersion(version);
    }
}
