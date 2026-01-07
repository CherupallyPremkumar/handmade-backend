package com.handmade.ecommerce.policy.service.store;

import com.handmade.ecommerce.policy.configuration.dao.PerformancePolicyRepository;
import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("performancePolicyEntityStore")
public class PerformancePolicyEntityStore implements EntityStore<PerformancePolicy> {

    @Autowired
    private PerformancePolicyRepository repository;

    @Override
    public PerformancePolicy retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(PerformancePolicy entity) {
        repository.save(entity);
    }
}
