package com.handmade.ecommerce.payment.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {
    private String checkoutId;
    private String buyerId;
    private BigDecimal amount;
    private String currency;
    private String idempotencyKey;
}
