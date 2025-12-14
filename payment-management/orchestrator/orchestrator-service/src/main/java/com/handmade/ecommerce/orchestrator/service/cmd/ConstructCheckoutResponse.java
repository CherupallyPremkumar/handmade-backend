package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

/**
 * Constructs the final payment response to return to the frontend.
 * 
 * Response includes:
 * - paymentId: Unique identifier for this payment
 * - checkoutUrl: PSP hosted page where user completes payment
 * - status: Current payment status (PENDING_USER_ACTION)
 * 
 * Frontend will redirect user to checkoutUrl.
 */
@Component
public class ConstructCheckoutResponse implements Command<PaymentExchange> {

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {
            PaymentResponse response = context.getResponse();

            if (response == null) {
                response = new PaymentResponse();
                context.setResponse(response);
            }

            // Response already populated by previous commands
            // Just validate it's complete
            if (response.getCheckoutUrl() == null || response.getCheckoutUrl().isEmpty()) {
                throw new IllegalStateException("Checkout URL not set");
            }

            if (response.getPaymentId() == null || response.getPaymentId().isEmpty()) {
                throw new IllegalStateException("Payment ID not set");
            }

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
