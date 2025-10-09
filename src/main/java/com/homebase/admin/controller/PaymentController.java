package com.homebase.admin.controller;

import com.homebase.admin.dto.CreatePaymentRequestDTO;
import com.homebase.admin.dto.CreatePaymentResponseDTO;
import com.homebase.admin.dto.PaymentCallbackDTO;
import com.homebase.admin.dto.PaymentStatusDTO;
import com.homebase.admin.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create a new payment request
     * POST /api/payment/create
     */
    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponseDTO> createPayment(
            @RequestBody CreatePaymentRequestDTO request) {
        CreatePaymentResponseDTO response = paymentService.createPayment(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Handle PhonePe payment callback
     * POST /api/payment/callback
     */
    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleCallback(
            @RequestBody PaymentCallbackDTO callback) {
        PaymentStatusDTO status = paymentService.handleCallback(callback.getTransactionId());
        
        return ResponseEntity.ok(Map.of(
            "success", "SUCCESS".equals(status.getStatus()),
            "orderId", status.getOrderId() != null ? status.getOrderId() : "",
            "message", status.getMessage() != null ? status.getMessage() : ""
        ));
    }

    /**
     * Check payment status
     * GET /api/payment/status/{transactionId}
     */
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<PaymentStatusDTO> checkPaymentStatus(
            @PathVariable String transactionId) {
        PaymentStatusDTO status = paymentService.checkPaymentStatus(transactionId);
        return ResponseEntity.ok(status);
    }
}
