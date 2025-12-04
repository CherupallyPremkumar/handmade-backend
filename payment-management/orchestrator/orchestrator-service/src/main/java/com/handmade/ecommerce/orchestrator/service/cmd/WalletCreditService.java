package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.orchestrator.WebhookExchange;
import com.handmade.ecommerce.paymentorder.model.PaymentOrder;
import com.handmade.ecommerce.paymentorder.PaymentOrderService;
import com.handmade.ecommerce.wallet.model.CreditWalletRequest;
import com.handmade.ecommerce.wallet.service.WalletService;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Credits each seller's wallet with their payment amount.
 * 
 * IMPORTANT: Wallet credit is PENDING until settlement period passes!
 * 
 * For each PaymentOrder:
 * - Seller A: +₹500 (PENDING)
 * - Seller B: +₹300 (PENDING)
 * - Seller C: +₹200 (PENDING)
 * 
 * Wallet operations:
 * 1. Find all PaymentOrders for this payment
 * 2. For each order, credit seller's wallet
 * 3. Mark wallet transaction as PENDING
 * 4. After 7-day hold, status changes to AVAILABLE
 * 
 * Wallet balance is used for:
 * - Displaying seller's pending/available funds
 * - Payout processing
 * - Financial reporting
 * 
 * Note: This is separate from ledger entries.
 * Wallet = Current balance (mutable)
 * Ledger = Transaction history (immutable)
 */
@Component
public class WalletCreditService implements Command<WebhookExchange> {

    @Autowired
    private WalletService walletService;

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

            // Credit each seller's wallet (constructor handles PENDING status and
            // description)
            for (PaymentOrder order : orders) {
                CreditWalletRequest request = new CreditWalletRequest(
                        order.getSellerId(),
                        order.getId(),
                        order.getAmount(),
                        order.getCurrency());
                walletService.credit(request);
            }

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
