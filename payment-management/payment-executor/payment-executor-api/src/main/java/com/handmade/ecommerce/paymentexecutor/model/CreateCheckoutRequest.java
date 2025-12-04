package com.handmade.ecommerce.paymentexecutor.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CreateCheckoutRequest {

    private String paymentId;
    private BigDecimal amount;
    private String currency;

    // Customer details
    private String sellerId;
    private String buyerName;
    private String buyerId;

    // Callback URLs
    private String successUrl;
    private String cancelUrl;
    private String webhookUrl;

    // Metadata
    private Map<String, String> metadata = new HashMap<>();

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(String key, String value) {
        this.metadata.put(key, value);
    }
}