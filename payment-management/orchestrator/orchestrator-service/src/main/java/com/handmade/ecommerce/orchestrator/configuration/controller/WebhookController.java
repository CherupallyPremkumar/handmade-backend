package com.handmade.ecommerce.orchestrator.api;

import com.handmade.ecommerce.ledger.service.LedgerService;
import com.handmade.ecommerce.payment.configuration.dao.PaymentOrderRepository;
import com.handmade.ecommerce.payment.dto.webhook.RazorpayWebhook;
import com.handmade.ecommerce.payment.dto.webhook.StripeWebhook;
import com.handmade.ecommerce.payment.model.PaymentOrder;
import com.handmade.ecommerce.payment.model.PaymentOrderStatus;
import com.handmade.ecommerce.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Webhook Controller
 * Handles asynchronous payment notifications from PSPs
 */
@Slf4j
@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentOrderRepository paymentOrderRepository;
    private final WalletService walletService;
    private final LedgerService ledgerService;

    /**
     * Handle Razorpay Webhook
     * Triggered when payment is captured or failed
     */
    @PostMapping("/razorpay")
    public ResponseEntity<Void> handleRazorpayWebhook(
            @RequestBody RazorpayWebhook webhook,
            @RequestHeader(value = "X-Razorpay-Signature", required = false) String signature) {

        log.info("Received Razorpay webhook: {}", webhook.getEvent());

        // TODO: Verify signature using webhook secret

        if ("payment.captured".equals(webhook.getEvent())) {
            processRazorpaySuccess(webhook);
        } else if ("payment.failed".equals(webhook.getEvent())) {
            processRazorpayFailure(webhook);
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Handle Stripe Webhook
     * Triggered when checkout session is completed
     */
    @PostMapping("/stripe")
    public ResponseEntity<Void> handleStripeWebhook(
            @RequestBody StripeWebhook webhook,
            @RequestHeader(value = "Stripe-Signature", required = false) String signature) {

        log.info("Received Stripe webhook: {}", webhook.getType());

        // TODO: Verify signature using webhook secret

        if ("checkout.session.completed".equals(webhook.getType())) {
            processStripeSuccess(webhook);
        }

        return ResponseEntity.ok().build();
    }

    private void processRazorpaySuccess(RazorpayWebhook webhook) {
        try {
            String paymentOrderId = webhook.getPayload().getOrder().getEntity().getReceipt();
            String pspPaymentId = webhook.getPayload().getPayment().getEntity().getId();

            log.info("Processing Razorpay success for Order: {}, PaymentId: {}", paymentOrderId, pspPaymentId);

            completePayment(paymentOrderId, pspPaymentId);

        } catch (Exception e) {
            log.error("Failed to process Razorpay success webhook", e);
        }
    }

    private void processRazorpayFailure(RazorpayWebhook webhook) {
        try {
            String paymentOrderId = webhook.getPayload().getOrder().getEntity().getReceipt();
            String errorDesc = "Payment Failed"; // Extract actual error if available

            log.info("Processing Razorpay failure for Order: {}", paymentOrderId);

            failPayment(paymentOrderId, errorDesc);

        } catch (Exception e) {
            log.error("Failed to process Razorpay failure webhook", e);
        }
    }

    private void processStripeSuccess(StripeWebhook webhook) {
        try {
            // We stored payment_order_id in metadata or client_reference_id
            String paymentOrderId = webhook.getData().getObject().getClient_reference_id();
            // Fallback to metadata if client_reference_id is null
            if (paymentOrderId == null && webhook.getData().getObject().getMetadata() != null) {
                paymentOrderId = webhook.getData().getObject().getMetadata().getPayment_order_id();
            }

            String pspPaymentId = webhook.getData().getObject().getPayment_intent();

            log.info("Processing Stripe success for Order: {}, PaymentIntent: {}", paymentOrderId, pspPaymentId);

            completePayment(paymentOrderId, pspPaymentId);

        } catch (Exception e) {
            log.error("Failed to process Stripe success webhook", e);
        }
    }

    private void completePayment(String paymentOrderId, String pspPaymentId) {
        Optional<PaymentOrder> orderOpt = paymentOrderRepository.findById(paymentOrderId);

        if (orderOpt.isEmpty()) {
            log.error("Payment order not found for webhook: {}", paymentOrderId);
            return;
        }

        PaymentOrder order = orderOpt.get();

        // Idempotency check: If already success, ignore
        if (PaymentOrderStatus.SUCCESS == order.getPaymentOrderStatus()) {
            log.info("Payment order {} already marked as SUCCESS, ignoring webhook", paymentOrderId);
            return;
        }

        // Update status
        order.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
        order.setPspReferenceId(pspPaymentId); // Store the actual payment ID (not just session ID)

        // Update Wallet
        if (!order.isWalletUpdated()) {
            walletService.credit(
                    order.getSellerId(),
                    order.getId(),
                    order.getAmount(),
                    "Payment Received via " + order.getCurrency());
            order.setWalletUpdated(true);
        }

        // Update Ledger
        if (!order.isLedgerUpdated()) {
            ledgerService.createDoubleEntry(
                    order.getId(),
                    "BUYER", // Placeholder
                    order.getSellerId(),
                    order.getAmount(),
                    order.getCurrency());
            order.setLedgerUpdated(true);
        }

        paymentOrderRepository.save(order);
        log.info("Successfully completed payment order: {}", paymentOrderId);
    }

    private void failPayment(String paymentOrderId, String errorReason) {
        Optional<PaymentOrder> orderOpt = paymentOrderRepository.findById(paymentOrderId);

        if (orderOpt.isEmpty()) {
            log.error("Payment order not found for webhook: {}", paymentOrderId);
            return;
        }

        PaymentOrder order = orderOpt.get();

        order.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
        order.setLastError(errorReason);

        paymentOrderRepository.save(order);
        log.info("Marked payment order {} as FAILED", paymentOrderId);
    }
}
