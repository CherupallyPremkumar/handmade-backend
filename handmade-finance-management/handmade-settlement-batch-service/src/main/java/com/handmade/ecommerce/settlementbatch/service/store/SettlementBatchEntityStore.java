package com.handmade.ecommerce.settlementbatch.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.finance.model.SettlementBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.settlementbatch.configuration.dao.SettlementBatchRepository;
import java.util.Optional;

public class SettlementBatchEntityStore implements EntityStore<SettlementBatch>{
    @Autowired private SettlementBatchRepository settlementbatchRepository;

	@Override
	public void store(SettlementBatch entity) {
        settlementbatchRepository.save(entity);
	}

	@Override
	public SettlementBatch retrieve(String id) {
        Optional<SettlementBatch> entity = settlementbatchRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SettlementBatch with ID " + id);
	}

}
