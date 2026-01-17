package com.handmade.ecommerce.paymentorder.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.payment.model.PaymentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.paymentorder.configuration.dao.PaymentOrderRepository;
import java.util.Optional;

public class PaymentOrderEntityStore implements EntityStore<PaymentOrder>{
    @Autowired private PaymentOrderRepository paymentorderRepository;

	@Override
	public void store(PaymentOrder entity) {
        paymentorderRepository.save(entity);
	}

	@Override
	public PaymentOrder retrieve(String id) {
        Optional<PaymentOrder> entity = paymentorderRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PaymentOrder with ID " + id);
	}

}
