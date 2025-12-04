package com.handmade.ecommerce.paymentorder.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.paymentorder.model.Paymentorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.paymentorder.configuration.dao.PaymentorderRepository;
import java.util.Optional;

public class PaymentorderEntityStore implements EntityStore<Paymentorder>{
    @Autowired private PaymentorderRepository paymentorderRepository;

	@Override
	public void store(Paymentorder entity) {
        paymentorderRepository.save(entity);
	}

	@Override
	public Paymentorder retrieve(String id) {
        Optional<Paymentorder> entity = paymentorderRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Paymentorder with ID " + id);
	}

}
