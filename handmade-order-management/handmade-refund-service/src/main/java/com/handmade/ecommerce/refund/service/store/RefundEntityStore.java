package com.handmade.ecommerce.refund.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.order.model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.refund.configuration.dao.RefundRepository;
import java.util.Optional;

public class RefundEntityStore implements EntityStore<Refund>{
    @Autowired private RefundRepository refundRepository;

	@Override
	public void store(Refund entity) {
        refundRepository.save(entity);
	}

	@Override
	public Refund retrieve(String id) {
        Optional<Refund> entity = refundRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Refund with ID " + id);
	}

}
