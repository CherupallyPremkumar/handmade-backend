package com.handmade.ecommerce.payment.dto.onboarding;

import lombok.Data;
import java.io.Serializable;

/**
 * Request to initiate payment gateway onboarding
 */
@Data
public class PaymentOnboardingRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sellerId; // Was referenceId
    private String gateway; // STRIPE, RAZORPAY, PAYPAL
    private String email;
    private String phone;
    private String businessName;
    private String businessType;
    private String country;
    private String returnUrl;
    private String refreshUrl;
}
