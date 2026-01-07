package com.handmade.ecommerce.policy.service.store;

import com.handmade.ecommerce.policy.configuration.dao.ReturnPolicyRepository;
import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component("returnPolicyEntityStore")
public class ReturnPolicyEntityStore implements EntityStore<ReturnPolicy> {

    @Autowired
    private ReturnPolicyRepository repository;

    @Override
    public ReturnPolicy retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(ReturnPolicy entity) {
        repository.save(entity);
    }

    public Optional<ReturnPolicy> findActivePolicy(String country, String category, LocalDate date) {
        return repository.findActivePolicy(country, category, date);
    }
}
