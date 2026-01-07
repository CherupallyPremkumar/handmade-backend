package com.handmade.ecommerce.policy.service.store;

import com.handmade.ecommerce.policy.configuration.dao.FraudPolicyRepository;
import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("fraudPolicyEntityStore")
public class FraudPolicyEntityStore implements EntityStore<FraudPolicy> {

    @Autowired
    private FraudPolicyRepository repository;

    @Override
    public FraudPolicy retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(FraudPolicy entity) {
        repository.save(entity);
    }
}
