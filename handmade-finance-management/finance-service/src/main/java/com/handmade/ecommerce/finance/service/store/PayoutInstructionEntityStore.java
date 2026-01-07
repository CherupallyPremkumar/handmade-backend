package com.handmade.ecommerce.finance.service.store;

import com.handmade.ecommerce.finance.configuration.dao.PayoutInstructionRepository;
import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PayoutInstructionEntityStore implements EntityStore<PayoutInstruction> {

    @Autowired
    private PayoutInstructionRepository repository;

    @Override
    public PayoutInstruction retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void store(PayoutInstruction entity) {
        repository.save(entity);
    }

    public List<PayoutInstruction> findBySellerId(String sellerId) {
        return repository.findBySellerId(sellerId);
    }
}
