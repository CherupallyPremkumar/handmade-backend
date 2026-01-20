package com.handmade.ecommerce.dispute.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.dispute.model.Dispute;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.dispute.configuration.dao.DisputeRepository;
import java.util.Optional;

public class DisputeEntityStore implements EntityStore<Dispute>{
    @Autowired private DisputeRepository disputeRepository;

	@Override
	public void store(Dispute entity) {
        disputeRepository.save(entity);
	}

	@Override
	public Dispute retrieve(String id) {
        Optional<Dispute> entity = disputeRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Dispute with ID " + id);
	}

}
