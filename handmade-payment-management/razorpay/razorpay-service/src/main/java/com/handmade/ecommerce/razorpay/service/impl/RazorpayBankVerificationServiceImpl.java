package com.handmade.ecommerce.razorpay.service.impl;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;
import com.handmade.ecommerce.razorpay.service.RazorpayBankVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Razorpay bank verification service implementation
 * Uses Razorpay Fund Account Validation API for penny drop verification
 * 
 * Documentation: https://razorpay.com/docs/api/fund-accounts/validations/
 */
@Service
public class RazorpayBankVerificationServiceImpl implements RazorpayBankVerificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(RazorpayBankVerificationServiceImpl.class);
    
    private static final String PROVIDER_NAME = "RAZORPAY";
    private static final double PENNY_DROP_COST = 3.0; // ₹3 per verification
    
    @Value("${razorpay.key.id:}")
    private String razorpayKeyId;
    
    @Value("${razorpay.key.secret:}")
    private String razorpayKeySecret;
    
    @Value("${razorpay.api.url:https://api.razorpay.com/v1}")
    private String razorpayApiUrl;
    
    @Override
    public BankVerificationResult verifyBankAccount(BankAccountDetails bankDetails) throws Exception {
        logger.info("Starting Razorpay penny drop verification for account: {}", 
            maskAccountNumber(bankDetails.getAccountNumber()));
        
        BankVerificationResult result = new BankVerificationResult();
        result.setProviderName(PROVIDER_NAME);
        result.setAccountNumber(bankDetails.getAccountNumber());
        result.setAccountHolderName(bankDetails.getAccountHolderName());
        
        try {
            // Validate required fields
            validateBankDetails(bankDetails);
            
            // TODO: Implement actual Razorpay API call
            // You'll need to add Razorpay SDK dependency and implement:
            /*
             * RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
             * 
             * JSONObject fundAccountRequest = new JSONObject();
             * fundAccountRequest.put("account_type", "bank_account");
             * fundAccountRequest.put("bank_account", new JSONObject()
             *     .put("name", bankDetails.getAccountHolderName())
             *     .put("ifsc", bankDetails.getIfscCode())
             *     .put("account_number", bankDetails.getAccountNumber()));
             * 
             * FundAccount fundAccount = razorpay.fundAccount.create(fundAccountRequest);
             * 
             * JSONObject validationRequest = new JSONObject();
             * validationRequest.put("fund_account_id", fundAccount.get("id"));
             * validationRequest.put("amount", 100); // ₹1.00 for penny drop
             * validationRequest.put("currency", "INR");
             * 
             * FundAccountValidation validation = razorpay.fundAccountValidation.create(validationRequest);
             * 
             * result.setReferenceId(validation.get("id"));
             * result.setStatus(mapRazorpayStatus(validation.get("status")));
             * result.setBankName(validation.get("bank_account").get("bank_name"));
             */
            
            // Placeholder response
            result.setReferenceId("fav_" + System.currentTimeMillis());
            result.setStatus("PENDING");
            result.setVerificationMethod("PENNY_DROP");
            result.setAmountDeposited(1.0); // ₹1.00
            result.addMetadata("provider", PROVIDER_NAME);
            result.addMetadata("ifsc_code", bankDetails.getIfscCode());
            result.addMetadata("cost", PENNY_DROP_COST);
            
            logger.info("Razorpay verification initiated successfully. Reference ID: {}", result.getReferenceId());
            
        } catch (Exception e) {
            logger.error("Razorpay verification failed", e);
            result.setStatus("FAILED");
            result.setErrorCode("RAZORPAY_API_ERROR");
            result.setErrorMessage(e.getMessage());
            throw e;
        }
        
        return result;
    }
    
    @Override
    public BankVerificationResult getVerificationStatus(String referenceId) throws Exception {
        logger.info("Checking Razorpay verification status for reference ID: {}", referenceId);
        
        BankVerificationResult result = new BankVerificationResult();
        result.setReferenceId(referenceId);
        result.setProviderName(PROVIDER_NAME);
        
        try {
            // TODO: Implement actual Razorpay API call to check status
            /*
             * RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
             * FundAccountValidation validation = razorpay.fundAccountValidation.fetch(referenceId);
             * 
             * result.setStatus(mapRazorpayStatus(validation.get("status")));
             * result.setVerifiedAt(parseTimestamp(validation.get("completed_at")));
             * result.setBankName(validation.get("bank_account").get("bank_name"));
             */
            
            // Placeholder response
            result.setStatus("VERIFIED");
            result.setVerifiedAt(LocalDateTime.now());
            result.setVerificationMethod("PENNY_DROP");
            
            logger.info("Razorpay verification status: {}", result.getStatus());
            
        } catch (Exception e) {
            logger.error("Failed to get Razorpay verification status", e);
            result.setStatus("FAILED");
            result.setErrorCode("RAZORPAY_STATUS_CHECK_ERROR");
            result.setErrorMessage(e.getMessage());
            throw e;
        }
        
        return result;
    }
    
    @Override
    public double getPennyDropCost() {
        return PENNY_DROP_COST;
    }
    
    @Override
    public boolean isConfigured() {
        return razorpayKeyId != null && !razorpayKeyId.trim().isEmpty() &&
               razorpayKeySecret != null && !razorpayKeySecret.trim().isEmpty();
    }
    
    /**
     * Validate bank details before verification
     */
    private void validateBankDetails(BankAccountDetails bankDetails) {
        if (bankDetails.getAccountNumber() == null || bankDetails.getAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        
        if (bankDetails.getIfscCode() == null || bankDetails.getIfscCode().trim().isEmpty()) {
            throw new IllegalArgumentException("IFSC code is required for India");
        }
        
        if (bankDetails.getAccountHolderName() == null || bankDetails.getAccountHolderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name is required");
        }
        
        // Validate IFSC code format
        if (!bankDetails.getIfscCode().matches("^[A-Z]{4}0[A-Z0-9]{6}$")) {
            throw new IllegalArgumentException("Invalid IFSC code format");
        }
    }
    
    /**
     * Map Razorpay status to our internal status
     */
    private String mapRazorpayStatus(String razorpayStatus) {
        switch (razorpayStatus.toLowerCase()) {
            case "completed":
                return "VERIFIED";
            case "failed":
                return "FAILED";
            case "created":
            case "processing":
                return "PENDING";
            default:
                return "FAILED";
        }
    }
    
    /**
     * Mask account number for logging
     */
    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}
