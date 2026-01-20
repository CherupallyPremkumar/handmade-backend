package com.handmade.ecommerce.payout.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.finance.model.Payout;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.payout.configuration.dao.PayoutRepository;
import java.util.Optional;

public class PayoutEntityStore implements EntityStore<Payout>{
    @Autowired private PayoutRepository payoutRepository;

	@Override
	public void store(Payout entity) {
        payoutRepository.save(entity);
	}

	@Override
	public Payout retrieve(String id) {
        Optional<Payout> entity = payoutRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Payout with ID " + id);
	}

}
