package com.handmade.ecommerce.payment.dto;

import lombok.Data;
import java.util.Map;

@Data
public class WebhookPayload {
    private String provider;
    private String eventType;
    private Map<String, Object> data;
    private String rawPayload;
}
