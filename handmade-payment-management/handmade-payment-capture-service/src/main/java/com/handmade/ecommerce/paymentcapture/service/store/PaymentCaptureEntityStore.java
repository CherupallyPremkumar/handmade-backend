package com.handmade.ecommerce.paymentcapture.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.payment.model.PaymentCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.paymentcapture.configuration.dao.PaymentCaptureRepository;
import java.util.Optional;

public class PaymentCaptureEntityStore implements EntityStore<PaymentCapture>{
    @Autowired private PaymentCaptureRepository paymentcaptureRepository;

	@Override
	public void store(PaymentCapture entity) {
        paymentcaptureRepository.save(entity);
	}

	@Override
	public PaymentCapture retrieve(String id) {
        Optional<PaymentCapture> entity = paymentcaptureRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PaymentCapture with ID " + id);
	}

}
