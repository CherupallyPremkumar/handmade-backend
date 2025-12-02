package com.handmade.ecommerce.orchestrator.context;

import org.chenile.core.context.ChenileExchange;
import org.chenile.owiz.OrchExecutor;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentEntryPoint {

    @Autowired
    private OrchExecutor<PaymentExchange> paymentOrchExecutor;

    public void execute(PaymentExchange paymentExchange) {
        try {
            paymentOrchExecutor.execute(paymentExchange);
        }catch(Exception e) {
            paymentExchange.setException(e);
        }
    }
}
