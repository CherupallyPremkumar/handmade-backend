package com.handmade.ecommerce.orchestrator.service.impl;


import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentOrderRequest;
import com.handmade.ecommerce.command.PaymentOrderResponse;
import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.orchestrator.service.PaymentProcessingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Payment Processing Service Implementation
 * Main orchestrator for payment flow following Alex Xu's design
 * 
 * Flow:
 * 1. Create PaymentEvent (parent record)
 * 2. For each PaymentOrder:
 * a. Check idempotency (prevent double payment)
 * b. Execute payment via PSP
 * c. Update wallet (credit seller)
 * d. Update ledger (double-entry)
 * 3. Mark payment as done
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessingServiceImpl implements PaymentProcessingService {

    com.handmade.ecommerce.orchestrator.context.PaymentEntryPoint paymentEntryPoint;

    private final PaymentRepository paymentRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentExecutor paymentExecutor;
    private final WalletService walletService;
    private final LedgerService ledgerService;

    @Override
    @Transactional
    public PaymentResponse processPayment(CreatePaymentRequest request) {
        log.info("Processing payment for checkoutId: {}, buyer: {}, orders: {}",
                request.getCheckoutId(),
                request.getBuyerId(),
                request.getPaymentOrders().size());

        // Step 1: Create Payment Event (parent record)
        Payment payment = createPayment(request);

        // Step 2: Process each payment order
        List<PaymentOrderResponse> orderResponses = new ArrayList<>();

        for (PaymentOrderRequest orderReq : request.getPaymentOrders()) {
            PaymentOrderResponse orderResponse = processPaymentOrder(payment, orderReq);
            orderResponses.add(orderResponse);
        }

        // Step 3: Check if all orders succeeded
        boolean allSuccess = orderResponses.stream()
                .allMatch(r -> "SUCCESS".equals(r.getStatus()));

        if (allSuccess) {
            payment.setIsPaymentDone(true);
            paymentRepository.save(payment);
            log.info("All payment orders succeeded for paymentId: {}", payment.getId());
        }

        // Step 4: Build response
        return buildPaymentResponse(payment, orderResponses);
    }

    @Override
    public PaymentResponse getPaymentStatus(String paymentOrderId) {
        log.info("Fetching payment status for paymentOrderId: {}", paymentOrderId);

        Optional<PaymentOrder> orderOpt = paymentOrderRepository.findById(paymentOrderId);

        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Payment order not found: " + paymentOrderId);
        }

        PaymentOrder order = orderOpt.get();
        Payment payment = paymentRepository.findById(order.getPaymentEventId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Build response with single order
        PaymentOrderResponse orderResponse = toPaymentOrderResponse(order);

        return PaymentResponse.builder()
                .paymentEventId(payment.getId())
                .status(order.getPaymentOrderStatus().name())
                .orders(List.of(orderResponse))
                .totalAmount(order.getAmount().toString())
                .currency(order.getCurrency())
                .build();
    }

    /**
     * Create Payment (parent record)
     */
    private Payment createPayment(CreatePaymentRequest request) {
        Payment payment = new Payment();
        payment.setCheckoutId(request.getCheckoutId());
        payment.setBuyerId(request.getBuyerId());
        payment.setIsPaymentDone(false);

        // Calculate total amount
        BigDecimal totalAmount = request.getPaymentOrders().stream()
                .map(o -> new BigDecimal(o.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        payment.setTotalAmount(totalAmount);

        Payment saved = paymentRepository.save(payment);
        log.info("Created Payment: {}", saved.getId());

        return saved;
    }

    /**
     * Process individual payment order
     * 
     * Key: Idempotency check prevents double payment!
     */
    /**
     * Process individual payment order
     * 
     * Key: Idempotency check prevents double payment!
     * 
     * Hosted Payment Page Flow:
     * 1. Check idempotency
     * 2. Create PaymentOrder (PENDING_USER_ACTION)
     * 3. Create Checkout Session with PSP
     * 4. Return Checkout URL
     */
    private PaymentOrderResponse processPaymentOrder(Payment payment, PaymentOrderRequest orderReq) {
        log.info("Processing payment order: {} for seller: {}",
                orderReq.getPaymentOrderId(),
                orderReq.getSellerId());

        // CRITICAL: Check idempotency (prevent double payment)
        Optional<PaymentOrder> existingOrder = paymentOrderRepository.findById(orderReq.getPaymentOrderId());

        if (existingOrder.isPresent()) {
            log.warn("Payment order already exists (idempotency): {}", orderReq.getPaymentOrderId());
            return toPaymentOrderResponse(existingOrder.get());
        }

        // Create new payment order
        PaymentOrder order = new PaymentOrder();
        order.setId(orderReq.getPaymentOrderId()); // Idempotency key from frontend!
        order.setPaymentEventId(payment.getId());
        order.setSellerId(orderReq.getSellerId());
        order.setAmount(new BigDecimal(orderReq.getAmount()));
        order.setCurrency(orderReq.getCurrency());
        order.setPaymentOrderStatus(PaymentOrderStatus.NOT_STARTED);
        order.setWalletUpdated(false);
        order.setLedgerUpdated(false);
        order.setRetryCount(0);

        // Save initial state
        order = paymentOrderRepository.save(order);
        log.info("Created PaymentOrder: {}", order.getId());

        try {
            // Step 2b: Create Checkout Session (Hosted Payment Page)
            // Instead of executing payment immediately, we get a URL for the user
            com.handmade.ecommerce.payment.model.CheckoutSession session = paymentExecutor.createCheckoutSession(order);

            // Update order with session info
            order.setPaymentOrderStatus(PaymentOrderStatus.PENDING_USER_ACTION);
            order.setPspReferenceId(session.getSessionId());

            order = paymentOrderRepository.save(order);
            log.info("Created checkout session for order: {}, URL: {}", order.getId(), session.getCheckoutUrl());

            // Return response with checkout URL
            return PaymentOrderResponse.builder()
                    .paymentOrderId(order.getId())
                    .sellerId(order.getSellerId())
                    .status(order.getPaymentOrderStatus().name())
                    .pspToken(session.getSessionId())
                    .amount(order.getAmount().toString())
                    .currency(order.getCurrency())
                    .retryCount(order.getRetryCount())
                    .checkoutUrl(session.getCheckoutUrl())
                    .build();

        } catch (Exception e) {
            log.error("Payment order failed: {}", order.getId(), e);

            order.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
            order.setLastError(e.getMessage());
            order.setRetryCount(order.getRetryCount() + 1);
            order = paymentOrderRepository.save(order);

            return toPaymentOrderResponse(order);
        }
    }

    /**
     * Convert PaymentOrder entity to DTO
     */
    private PaymentOrderResponse toPaymentOrderResponse(PaymentOrder order) {
        return PaymentOrderResponse.builder()
                .paymentOrderId(order.getId())
                .sellerId(order.getSellerId())
                .status(order.getPaymentOrderStatus().name())
                .pspToken(order.getPspReferenceId())
                .amount(order.getAmount().toString())
                .currency(order.getCurrency())
                .errorMessage(order.getLastError())
                .retryCount(order.getRetryCount())
                .build();
    }

    /**
     * Build final payment response
     */
    private PaymentResponse buildPaymentResponse(Payment payment, List<PaymentOrderResponse> orders) {
        // Determine overall status
        boolean allSuccess = orders.stream().allMatch(o -> "SUCCESS".equals(o.getStatus()));
        boolean allFailed = orders.stream().allMatch(o -> "FAILED".equals(o.getStatus()));

        String status;
        if (allSuccess) {
            status = "SUCCESS";
        } else if (allFailed) {
            status = "FAILED";
        } else {
            status = "PARTIAL";
        }

        String checkoutUrl = null;
        String checkoutSessionId = null;

        if (!orders.isEmpty()) {
            // Take the first order's checkout URL (assuming single checkout flow)
            checkoutUrl = orders.get(0).getCheckoutUrl();
            checkoutSessionId = orders.get(0).getPspToken();
        }

        return PaymentResponse.builder()
                .paymentEventId(payment.getId())
                .status(status)
                .orders(orders)
                .totalAmount(payment.getTotalAmount().toString())
                .currency(orders.isEmpty() ? "INR" : orders.get(0).getCurrency())
                .checkoutUrl(checkoutUrl)
                .checkoutSessionId(checkoutSessionId)
                .build();
    }
}
