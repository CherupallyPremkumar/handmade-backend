package com.handmade.ecommerce.paymentauthorization.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.payment.model.PaymentAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.paymentauthorization.configuration.dao.PaymentAuthorizationRepository;
import java.util.Optional;

public class PaymentAuthorizationEntityStore implements EntityStore<PaymentAuthorization>{
    @Autowired private PaymentAuthorizationRepository paymentauthorizationRepository;

	@Override
	public void store(PaymentAuthorization entity) {
        paymentauthorizationRepository.save(entity);
	}

	@Override
	public PaymentAuthorization retrieve(String id) {
        Optional<PaymentAuthorization> entity = paymentauthorizationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PaymentAuthorization with ID " + id);
	}

}
