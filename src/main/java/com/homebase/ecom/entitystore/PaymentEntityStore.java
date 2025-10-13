package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Payment;
import com.homebase.ecom.repository.PaymentRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntityStore implements EntityStore<Payment> {
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void store(Payment entity) {
    }

    @Override
    public Payment retrieve(String id) {
        return null;
    }
}
