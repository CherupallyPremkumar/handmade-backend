package com.handmade.ecommerce.payment.dto.onboarding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Response after initiating payment gateway onboarding
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOnboardingResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountId;
    private String onboardingUrl;
    private String gateway;
    private boolean isComplete;
    private String message;
}
