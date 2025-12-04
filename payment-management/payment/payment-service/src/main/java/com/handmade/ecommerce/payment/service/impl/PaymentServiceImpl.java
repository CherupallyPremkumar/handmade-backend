package com.handmade.ecommerce.payment.service.impl;

import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.payment.model.Payment;
import com.handmade.ecommerce.payment.service.PaymentService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;


public class PaymentServiceImpl extends StateEntityServiceImpl<Payment> implements PaymentService {


    public PaymentServiceImpl(STM<Payment> stm, STMActionsInfoProvider stmActionsInfoProvider, EntityStore<Payment> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public PaymentResponse processPayment(CreatePaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse getPaymentStatus(String paymentId) {
        return null;
    }
}
