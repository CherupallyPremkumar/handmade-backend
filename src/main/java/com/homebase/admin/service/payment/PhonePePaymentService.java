package com.homebase.admin.service.payment;

import com.homebase.admin.dto.CreatePaymentRequest;
import com.homebase.admin.dto.CreatePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * PhonePe Payment Gateway Integration
 * 
 * Documentation: https://developer.phonepe.com/v1/docs/payment-gateway
 * 
 * Flow:
 * 1. Create payment request with order details
 * 2. Generate checksum for security
 * 3. Call PhonePe API to get payment URL
 * 4. Redirect customer to payment URL
 * 5. PhonePe calls webhook after payment
 * 6. Verify callback signature
 * 7. Update order status
 */
@Service
public class PhonePePaymentService implements PaymentGatewayService {

    @Value("${phonepe.merchant.id:MERCHANTUAT}")
    private String merchantId;

    @Value("${phonepe.salt.key:099eb0cd-02cf-4e2a-8aca-3e6c6aff0399}")
    private String saltKey;

    @Value("${phonepe.salt.index:1}")
    private String saltIndex;

    @Value("${phonepe.api.url:https://api-preprod.phonepe.com/apis/pg-sandbox}")
    private String apiUrl;

    @Value("${phonepe.redirect.url:http://localhost:3000/payment/success}")
    private String defaultRedirectUrl;

    @Value("${phonepe.callback.url:http://localhost:8080/api/user/payment/callback}")
    private String defaultCallbackUrl;

    @Override
    public CreatePaymentResponse initiatePayment(CreatePaymentRequest request) {
        try {
            // Generate unique merchant transaction ID
            String merchantTransactionId = generateMerchantTransactionId(request.getOrderId());

            // Build payment request payload
            Map<String, Object> payload = buildPaymentPayload(request, merchantTransactionId);

            // Convert to JSON and encode
            String jsonPayload = convertToJson(payload);
            String base64Payload = Base64.getEncoder().encodeToString(
                    jsonPayload.getBytes(StandardCharsets.UTF_8)
            );

            // Generate checksum for security
            String checksum = generateChecksum(base64Payload);

            // In production, call actual PhonePe API
            // String paymentUrl = callPhonePeAPI(base64Payload, checksum);

            // For now, return mock response
            String paymentUrl = apiUrl + "/pay/" + merchantTransactionId;

            CreatePaymentResponse response = new CreatePaymentResponse();
            response.setSuccess(true);
            response.setPaymentUrl(paymentUrl);
            response.setTransactionId(merchantTransactionId);
            response.setMerchantTransactionId(merchantTransactionId);
            response.setMessage("Payment initiated successfully");

            return response;

        } catch (Exception e) {
            CreatePaymentResponse response = new CreatePaymentResponse();
            response.setSuccess(false);
            response.setMessage("Failed to initiate payment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public PaymentVerificationResult verifyPayment(String transactionId) {
        try {
            // Build verification request
            String endpoint = "/pg/v1/status/" + merchantId + "/" + transactionId;
            String checksum = generateChecksum(endpoint + saltKey);

            // In production, call PhonePe status API
            // Response response = callPhonePeStatusAPI(endpoint, checksum);

            // Mock response for development
            PaymentVerificationResult result = new PaymentVerificationResult(true, "SUCCESS");
            result.setTransactionId(transactionId);
            result.setMessage("Payment verified successfully");

            return result;

        } catch (Exception e) {
            PaymentVerificationResult result = new PaymentVerificationResult(false, "FAILED");
            result.setMessage("Verification failed: " + e.getMessage());
            return result;
        }
    }

    @Override
    public PaymentCallbackResult handleCallback(String callbackData) {
        try {
            // Parse callback data (JSON)
            Map<String, Object> data = parseCallbackData(callbackData);

            // Verify signature
            String receivedChecksum = (String) data.get("checksum");
            boolean signatureValid = verifyCallbackSignature(callbackData, receivedChecksum);

            if (!signatureValid) {
                PaymentCallbackResult result = new PaymentCallbackResult(false, "FAILED");
                result.setMessage("Invalid signature");
                result.setSignatureValid(false);
                return result;
            }

            // Extract payment details
            String status = (String) data.get("code");
            String transactionId = (String) data.get("transactionId");
            String merchantTransactionId = (String) data.get("merchantTransactionId");

            PaymentCallbackResult result = new PaymentCallbackResult(
                    "PAYMENT_SUCCESS".equals(status),
                    status
            );
            result.setTransactionId(transactionId);
            result.setMerchantTransactionId(merchantTransactionId);
            result.setSignatureValid(true);
            result.setMessage("Callback processed successfully");

            return result;

        } catch (Exception e) {
            PaymentCallbackResult result = new PaymentCallbackResult(false, "ERROR");
            result.setMessage("Callback processing failed: " + e.getMessage());
            return result;
        }
    }

    @Override
    public String getGatewayName() {
        return "PhonePe";
    }

    // Helper Methods

    private Map<String, Object> buildPaymentPayload(CreatePaymentRequest request, String merchantTransactionId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("merchantId", merchantId);
        payload.put("merchantTransactionId", merchantTransactionId);
        payload.put("merchantUserId", request.getCustomerPhone());
        payload.put("amount", request.getAmount().multiply(new java.math.BigDecimal("100")).longValue()); // Convert to paise
        payload.put("redirectUrl", request.getRedirectUrl() != null ? request.getRedirectUrl() : defaultRedirectUrl);
        payload.put("redirectMode", "POST");
        payload.put("callbackUrl", request.getCallbackUrl() != null ? request.getCallbackUrl() : defaultCallbackUrl);

        // Payment instrument
        Map<String, String> paymentInstrument = new HashMap<>();
        paymentInstrument.put("type", "PAY_PAGE");
        payload.put("paymentInstrument", paymentInstrument);

        return payload;
    }

    private String generateMerchantTransactionId(String orderId) {
        return "MT" + orderId + System.currentTimeMillis();
    }

    private String generateChecksum(String data) {
        try {
            String input = data + "/pg/v1/pay" + saltKey;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash) + "###" + saltIndex;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate checksum", e);
        }
    }

    private boolean verifyCallbackSignature(String data, String receivedChecksum) {
        try {
            String calculatedChecksum = generateChecksum(data);
            return calculatedChecksum.equals(receivedChecksum);
        } catch (Exception e) {
            return false;
        }
    }

    private String convertToJson(Map<String, Object> data) {
        // Simple JSON conversion (in production, use Jackson or Gson)
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() instanceof String) {
                json.append("\"").append(entry.getValue()).append("\"");
            } else if (entry.getValue() instanceof Map) {
                json.append(convertToJson((Map<String, Object>) entry.getValue()));
            } else {
                json.append(entry.getValue());
            }
            first = false;
        }
        json.append("}");
        return json.toString();
    }

    private Map<String, Object> parseCallbackData(String callbackData) {
        // Simple JSON parsing (in production, use Jackson or Gson)
        Map<String, Object> data = new HashMap<>();
        // TODO: Implement proper JSON parsing
        return data;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
