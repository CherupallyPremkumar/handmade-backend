package com.handmade.ecommerce.paymentrefundtransaction.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.payment.model.PaymentRefundTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.paymentrefundtransaction.configuration.dao.PaymentRefundTransactionRepository;
import java.util.Optional;

public class PaymentRefundTransactionEntityStore implements EntityStore<PaymentRefundTransaction>{
    @Autowired private PaymentRefundTransactionRepository paymentrefundtransactionRepository;

	@Override
	public void store(PaymentRefundTransaction entity) {
        paymentrefundtransactionRepository.save(entity);
	}

	@Override
	public PaymentRefundTransaction retrieve(String id) {
        Optional<PaymentRefundTransaction> entity = paymentrefundtransactionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PaymentRefundTransaction with ID " + id);
	}

}
