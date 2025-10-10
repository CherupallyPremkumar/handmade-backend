package com.homebase.admin.controller.user;

import com.homebase.admin.dto.CreatePaymentRequest;
import com.homebase.admin.dto.CreatePaymentResponse;
import com.homebase.admin.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Customer-facing payment controller
 * Handles payment initiation, callbacks, and status checks
 */
@RestController
@RequestMapping("/api/user/payment")
public class UserPaymentController {

    private final PaymentService paymentService;

    public UserPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * POST /api/user/payment/create?tenantId={tenantId}
     * Initiate payment for an order
     * 
     * Called after order creation to start payment process
     * Returns payment URL to redirect customer to PhonePe
     */
    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        CreatePaymentResponse response = paymentService.createPayment(request, tenantId);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/user/payment/callback?tenantId={tenantId}
     * Payment callback webhook from PhonePe
     * 
     * PhonePe calls this endpoint after payment completion
     * Updates order and payment status based on result
     */
    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleCallback(
            @RequestBody String callbackData,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        Map<String, Object> result = paymentService.handlePaymentCallback(callbackData, tenantId);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/user/payment/status/{transactionId}?tenantId={tenantId}
     * Check payment status
     * 
     * Used to verify payment status from frontend
     */
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<Map<String, Object>> checkPaymentStatus(
            @PathVariable String transactionId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        Map<String, Object> status = paymentService.checkPaymentStatus(transactionId, tenantId);
        return ResponseEntity.ok(status);
    }
}
