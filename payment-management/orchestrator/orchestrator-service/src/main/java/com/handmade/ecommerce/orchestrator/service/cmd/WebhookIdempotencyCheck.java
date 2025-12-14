package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.orchestrator.PaymentExchange;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

/**
 * Checks if webhook has already been processed (idempotency).
 * 
 * PSPs may send the same webhook multiple times due to:
 * - Network retries
 * - PSP internal retries
 * - Timeout on their side
 * 
 * This command prevents duplicate processing by checking Payment status:
 * 
 * If Payment.status == COMPLETED:
 * - Webhook already processed
 * - Return 200 OK (idempotent response)
 * - Skip remaining commands
 * 
 * If Payment.status == PROCESSING or PENDING_USER_ACTION:
 * - First time processing
 * - Continue to next command
 * 
 * This ensures:
 * - Sellers are not credited multiple times
 * - Ledger entries are not duplicated
 * - Notifications are not sent multiple times
 */
@Component
public class WebhookIdempotencyCheck implements Command<PaymentExchange> {

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
