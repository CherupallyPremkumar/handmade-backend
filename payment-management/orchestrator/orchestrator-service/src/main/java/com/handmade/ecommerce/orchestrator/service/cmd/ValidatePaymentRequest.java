package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentOrderRequest;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidatePaymentRequest implements Command<PaymentExchange> {
    @Override
    public void execute(PaymentExchange context) throws Exception {

        CreatePaymentRequest request = context.getRequest();
        if (request == null) {
            throw new IllegalArgumentException("Payment request is null");
        }

        if (request.getBuyerId() == null || request.getBuyerId().isEmpty()) {
            throw new IllegalArgumentException("Buyer ID is required");
        }

        if (request.getPaymentOrders() == null || request.getPaymentOrders().isEmpty()) {
            throw new IllegalArgumentException("Payment orders are required");
        }

        // Validate each payment order
        for (PaymentOrderRequest order : request.getPaymentOrders()) {
            if (order.getSellerId() == null || order.getSellerId().isEmpty()) {
                throw new IllegalArgumentException("Seller ID is required");
            }

            if (order.getAmount() == null || order.getAmount().compareTo(String.valueOf(BigDecimal.ZERO)) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }

            if (order.getCurrency() == null || order.getCurrency().isEmpty()) {
                throw new IllegalArgumentException("Currency is required");
            }
        }
        if (request.getIdempotencyKey() == null || request.getIdempotencyKey().isEmpty()) {
            throw new IllegalArgumentException("Idempotency key is required");
        }

    }
}
