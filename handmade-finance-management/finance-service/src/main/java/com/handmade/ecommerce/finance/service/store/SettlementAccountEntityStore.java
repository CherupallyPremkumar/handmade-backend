package com.handmade.ecommerce.finance.service.store;

import com.handmade.ecommerce.finance.configuration.dao.SettlementAccountRepository;
import com.handmade.ecommerce.finance.domain.aggregate.SettlementAccount;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SettlementAccountEntityStore implements EntityStore<SettlementAccount> {

    @Autowired
    private SettlementAccountRepository repository;

    @Override
    public SettlementAccount retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(SettlementAccount entity) {
        repository.save(entity);
    }

    public Optional<SettlementAccount> findBySellerId(String sellerId) {
        return repository.findBySellerId(sellerId);
    }
}
