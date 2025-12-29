package com.handmade.ecommerce.payment.service.provider.impl;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;
import com.handmade.ecommerce.payment.service.provider.BankVerificationProvider;
import com.handmade.ecommerce.razorpay.service.RazorpayBankVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Razorpay bank verification provider (ADAPTER)
 * Delegates to RazorpayBankVerificationService in razorpay module
 */
@Service
public class RazorpayVerificationProvider implements BankVerificationProvider {

    private static final Logger logger = LoggerFactory.getLogger(RazorpayVerificationProvider.class);

    private static final String PROVIDER_NAME = "RAZORPAY";

    // Supported countries
    private static final Set<String> SUPPORTED_COUNTRIES = new HashSet<>();
    static {
        SUPPORTED_COUNTRIES.add("IN"); // India
    }

    @Autowired
    private RazorpayBankVerificationService razorpayService;

    @Override
    public BankVerificationResult verify(BankAccountDetails bankDetails) throws Exception {
        logger.info("Delegating to Razorpay service for verification");
        return razorpayService.verifyBankAccount(bankDetails);
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
        logger.info("Delegating to Razorpay service for status check");
        return razorpayService.getVerificationStatus(referenceId);
    }

    @Override
    public double getVerificationCost() {
        return razorpayService.getPennyDropCost();
    }
}
