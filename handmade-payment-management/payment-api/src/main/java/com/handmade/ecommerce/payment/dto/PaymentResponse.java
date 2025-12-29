package com.handmade.ecommerce.payment.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private String paymentId;
    private String status;
    private String checkoutUrl;
    private String message;
    private boolean success;
}
