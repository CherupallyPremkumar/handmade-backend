package com.handmade.ecommerce.policy.service.store;

import com.handmade.ecommerce.policy.configuration.dao.InventoryPolicyRepository;
import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component("inventoryPolicyEntityStore")
public class InventoryPolicyEntityStore implements EntityStore<InventoryPolicy> {

    @Autowired
    private InventoryPolicyRepository repository;

    @Override
    public InventoryPolicy retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(InventoryPolicy entity) {
        repository.save(entity);
    }

    public Optional<InventoryPolicy> findActivePolicy(String country, String category, LocalDate date) {
        return repository.findActivePolicy(country, category, date);
    }
}
