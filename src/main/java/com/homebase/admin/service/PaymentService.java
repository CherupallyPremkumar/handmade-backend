package com.homebase.admin.service;

import com.homebase.admin.dto.CreatePaymentRequest;
import com.homebase.admin.dto.CreatePaymentResponse;
import com.homebase.admin.entity.Order;
import com.homebase.admin.entity.Payment;
import com.homebase.admin.repository.OrderRepository;
import com.homebase.admin.repository.PaymentRepository;
import com.homebase.admin.service.payment.PaymentCallbackResult;
import com.homebase.admin.service.payment.PaymentGatewayService;
import com.homebase.admin.service.payment.PaymentVerificationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Payment Service - Business logic for payment processing
 * Coordinates between PaymentGateway and Order/Payment entities
 */
@Service
public class PaymentService {

    private final PaymentGatewayService paymentGatewayService;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserOrderService userOrderService;

    public PaymentService(PaymentGatewayService paymentGatewayService,
                         PaymentRepository paymentRepository,
                         OrderRepository orderRepository,
                         UserOrderService userOrderService) {
        this.paymentGatewayService = paymentGatewayService;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userOrderService = userOrderService;
    }

    /**
     * Create payment for an order
     */
    @Transactional
    public CreatePaymentResponse createPayment(CreatePaymentRequest request, String tenantId) {
        // Get order
        Order order = orderRepository.findByOrderNumberAndTenantId(request.getOrderId(), tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Validate order status
        if (order.getPaymentStatus() == Order.PaymentStatus.PAID) {
            throw new IllegalStateException("Order already paid");
        }

        // Create payment entity
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setMethod(Payment.PaymentMethod.PHONEPE);
        payment.setGateway(Payment.PaymentGateway.PHONEPE);
        payment.setTenantId(tenantId);

        // Initiate payment with gateway
        CreatePaymentResponse response = paymentGatewayService.initiatePayment(request);

        if (response.isSuccess()) {
            payment.setTransactionId(response.getTransactionId());
            payment.setMerchantTransactionId(response.getMerchantTransactionId());
            payment.setStatus(Payment.PaymentStatus.PROCESSING);
        } else {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setFailureReason(response.getMessage());
        }

        paymentRepository.save(payment);

        return response;
    }

    /**
     * Handle payment callback from gateway
     */
    @Transactional
    public Map<String, Object> handlePaymentCallback(String callbackData, String tenantId) {
        Map<String, Object> result = new HashMap<>();

        try {
            // Process callback
            PaymentCallbackResult callbackResult = paymentGatewayService.handleCallback(callbackData);

            if (!callbackResult.isSignatureValid()) {
                result.put("success", false);
                result.put("message", "Invalid signature");
                return result;
            }

            // Get payment
            Payment payment = paymentRepository.findByTransactionId(callbackResult.getTransactionId())
                    .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

            // Update payment status
            if (callbackResult.isSuccess()) {
                payment.setStatus(Payment.PaymentStatus.SUCCESS);
                payment.setGatewayResponse(callbackResult.getMessage());

                // Update order
                userOrderService.updateOrderAfterPayment(
                        payment.getOrder().getOrderNumber(),
                        callbackResult.getTransactionId(),
                        Payment.PaymentStatus.SUCCESS,
                        tenantId
                );

                result.put("success", true);
                result.put("orderId", payment.getOrder().getOrderNumber());
                result.put("message", "Payment successful");
            } else {
                payment.setStatus(Payment.PaymentStatus.FAILED);
                payment.setFailureReason(callbackResult.getMessage());

                // Update order
                userOrderService.updateOrderAfterPayment(
                        payment.getOrder().getOrderNumber(),
                        callbackResult.getTransactionId(),
                        Payment.PaymentStatus.FAILED,
                        tenantId
                );

                result.put("success", false);
                result.put("message", "Payment failed");
            }

            paymentRepository.save(payment);

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error processing callback: " + e.getMessage());
        }

        return result;
    }

    /**
     * Check payment status
     */
    @Transactional(readOnly = true)
    public Map<String, Object> checkPaymentStatus(String transactionId, String tenantId) {
        Map<String, Object> result = new HashMap<>();

        try {
            // Verify with gateway
            PaymentVerificationResult verificationResult = paymentGatewayService.verifyPayment(transactionId);

            // Get payment from database
            Payment payment = paymentRepository.findByTransactionId(transactionId)
                    .orElse(null);

            if (payment != null) {
                result.put("status", payment.getStatus().name());
                result.put("orderId", payment.getOrder().getOrderNumber());
                result.put("amount", payment.getAmount());
            } else {
                result.put("status", verificationResult.getStatus());
            }

            result.put("success", verificationResult.isSuccess());
            result.put("message", verificationResult.getMessage());

        } catch (Exception e) {
            result.put("success", false);
            result.put("status", "ERROR");
            result.put("message", "Error checking status: " + e.getMessage());
        }

        return result;
    }
}
