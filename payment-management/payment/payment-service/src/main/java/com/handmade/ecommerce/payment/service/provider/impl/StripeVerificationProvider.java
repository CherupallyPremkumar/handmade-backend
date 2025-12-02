package com.handmade.ecommerce.payment.service.provider.impl;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;
import com.handmade.ecommerce.payment.service.provider.BankVerificationProvider;
import com.handmade.ecommerce.stripe.service.StripeBankVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Stripe bank verification provider (ADAPTER)
 * Delegates to StripeBankVerificationService in stripe module
 */
@Service
public class StripeVerificationProvider implements BankVerificationProvider {

    private static final Logger logger = LoggerFactory.getLogger(StripeVerificationProvider.class);

    private static final String PROVIDER_NAME = "STRIPE";

    // Supported countries
    private static final Set<String> SUPPORTED_COUNTRIES = new HashSet<>();
    static {
        SUPPORTED_COUNTRIES.add("US"); // USA (ACH)
        SUPPORTED_COUNTRIES.add("GB"); // UK (BACS)
        SUPPORTED_COUNTRIES.add("CA"); // Canada
        SUPPORTED_COUNTRIES.add("AU"); // Australia
        SUPPORTED_COUNTRIES.add("NZ"); // New Zealand
        // European countries for SEPA
        SUPPORTED_COUNTRIES.add("DE"); // Germany
        SUPPORTED_COUNTRIES.add("FR"); // France
        SUPPORTED_COUNTRIES.add("ES"); // Spain
        SUPPORTED_COUNTRIES.add("IT"); // Italy
        SUPPORTED_COUNTRIES.add("NL"); // Netherlands
    }

    @Autowired
    private StripeBankVerificationService stripeService;

    @Override
    public BankVerificationResult verify(BankAccountDetails bankDetails) throws Exception {
        logger.info("Delegating to Stripe service for verification");
        return stripeService.verifyBankAccount(bankDetails);
    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    @Override
    public boolean supportsCountry(String countryCode) {
        return SUPPORTED_COUNTRIES.contains(countryCode.toUpperCase());
    }

    @Override
    public BankVerificationResult getVerificationStatus(String referenceId) throws Exception {
        logger.info("Delegating to Stripe service for status check");
        return stripeService.getVerificationStatus(referenceId);
    }

    @Override
    public double getVerificationCost() {
        // Return default cost for USA
        return stripeService.getVerificationCost("US");
    }
}
