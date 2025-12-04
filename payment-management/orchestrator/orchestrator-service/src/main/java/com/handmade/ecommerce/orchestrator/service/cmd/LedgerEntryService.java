package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.ledger.service.LedgerService;
import com.handmade.ecommerce.orchestrator.WebhookExchange;
import com.handmade.ecommerce.paymentorder.PaymentOrderService;
import com.handmade.ecommerce.paymentorder.model.PaymentOrder;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Creates ledger records for payment transactions (PAY-IN phase).
 * 
 * Delegates to LedgerService.recordPayIn() which handles all account logic:
 * 1. Platform Account (CREDIT) - money arrived
 * 2. Seller Virtual Wallet (CREDIT) - seller is owed
 * 3. Platform Payable (DEBIT) - liability created
 * 
 * At this point:
 * - Money is in platform's bank account
 * - Seller wallet shows virtual balance
 * - NO actual bank transfer to seller yet
 * 
 * Later during PAYOUT, different entries are created:
 * - Seller Virtual Wallet (DEBIT) - reduce virtual balance
 * - Platform Account (DEBIT) - money leaving
 * - Platform Payable (CREDIT) - liability settled
 */
@Component
public class LedgerEntryService implements Command<WebhookExchange> {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Override
    public void execute(WebhookExchange context) throws Exception {
        try {
            String paymentId = context.getParsedPayload().getPaymentId();
            List<PaymentOrder> orders = paymentOrderService.findByPaymentId(paymentId);
            if (orders == null || orders.isEmpty()) {
                return;
            }
            // Record pay-in for each seller (LedgerService handles all account logic)
            for (PaymentOrder order : orders) {
                ledgerService.recordPayIn(
                        order.getSellerId(),
                        order.getAmount(),
                        order.getCurrency(),
                        order.getId());
            }

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
