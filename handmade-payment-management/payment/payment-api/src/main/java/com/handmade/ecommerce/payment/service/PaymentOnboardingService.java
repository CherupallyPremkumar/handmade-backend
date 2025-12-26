package com.handmade.ecommerce.payment.service;

import com.handmade.ecommerce.payment.dto.onboarding.PaymentAccountStatus;
import com.handmade.ecommerce.payment.dto.onboarding.PaymentOnboardingRequest;
import com.handmade.ecommerce.payment.dto.onboarding.PaymentOnboardingResponse;

/**
 * Service for handling payment gateway onboarding (Stripe Connect, Razorpay
 * Route, etc.)
 */
public interface PaymentOnboardingService {

    /**
     * Initiate onboarding for a merchant/seller
     */
    PaymentOnboardingResponse initiateOnboarding(PaymentOnboardingRequest request);

    /**
     * Get account status
     */
    PaymentAccountStatus getAccountStatus(String accountId, String gateway);

    /**
     * Refresh onboarding link
     */
    String refreshOnboardingLink(String accountId, String gateway, String returnUrl, String refreshUrl);

    /**
     * Handle webhook events related to onboarding
     */
    void handleWebhook(String gateway, String payload, String signature);
}
