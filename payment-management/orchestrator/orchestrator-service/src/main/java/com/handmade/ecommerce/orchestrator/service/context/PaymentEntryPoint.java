package com.handmade.ecommerce.orchestrator.service.context;

import com.handmade.ecommerce.orchestrator.PaymentExchange;
import org.chenile.owiz.OrchExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntryPoint {

    @Autowired
    private OrchExecutor<PaymentExchange> paymentOrchExecutor;

    public PaymentExchange execute(PaymentExchange paymentExchange) {
        try {
            paymentOrchExecutor.execute(paymentExchange);
        }catch(Exception e) {
            paymentExchange.setException(e);
        }
        return paymentExchange;
    }
}
