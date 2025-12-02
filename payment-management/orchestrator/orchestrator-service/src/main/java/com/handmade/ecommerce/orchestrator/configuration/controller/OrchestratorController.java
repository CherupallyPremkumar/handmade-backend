package com.handmade.ecommerce.orchestrator.configuration.controller;

import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.orchestrator.PaymentProcessingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.orchestrator.model.Orchestrator;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "orchestratorService", serviceName = "_orchestratorService_",
		healthCheckerName = "orchestratorHealthChecker")
public class OrchestratorController extends ControllerSupport{
	
    @PostMapping("/orchestrator")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Orchestrator>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Orchestrator entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/orchestrator/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Orchestrator>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }

    private final PaymentProcessingService paymentProcessingService;

    /**
     * Process payment request
     *
     * Flow:
     * 1. Validate request
     * 2. Create PaymentEvent
     * 3. Process each PaymentOrder
     * 4. Execute PSP payment
     * 5. Update Wallet
     * 6. Update Ledger
     *
     * @param request Payment request with payment orders
     * @return Payment response with status and tokens
     */
    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        log.info("Processing payment for checkoutId: {}, buyer: {}, orders: {}",
                request.getCheckoutId(),
                request.getBuyerId(),
                request.getPaymentOrders().size());

        try {
            PaymentResponse response = paymentProcessingService.processPayment(request);

            log.info("Payment processed successfully. PaymentEventId: {}, Status: {}",
                    response.getPaymentEventId(),
                    response.getStatus());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Payment processing failed for checkoutId: {}",
                    request.getCheckoutId(), e);
            throw e;
        }
    }

    /**
     * Get payment order status
     *
     * @param paymentOrderId Payment order ID (idempotency key)
     * @return Payment order response with current status
     */
    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(
            @PathVariable String paymentOrderId) {

        log.info("Fetching payment status for paymentOrderId: {}", paymentOrderId);

        PaymentResponse response = paymentProcessingService.getPaymentStatus(paymentOrderId);

        return ResponseEntity.ok(response);
    }
}
