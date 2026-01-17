package com.handmade.ecommerce.paymenttransaction.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.payment.model.PaymentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.paymenttransaction.configuration.dao.PaymentTransactionRepository;
import java.util.Optional;

public class PaymentTransactionEntityStore implements EntityStore<PaymentTransaction>{
    @Autowired private PaymentTransactionRepository paymenttransactionRepository;

	@Override
	public void store(PaymentTransaction entity) {
        paymenttransactionRepository.save(entity);
	}

	@Override
	public PaymentTransaction retrieve(String id) {
        Optional<PaymentTransaction> entity = paymenttransactionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PaymentTransaction with ID " + id);
	}

}
