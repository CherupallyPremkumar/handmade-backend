package com.handmade.ecommerce.payment.service.impl;

import com.handmade.ecommerce.payment.configuration.dao.BankVerificationRepository;
import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;
import com.handmade.ecommerce.payment.model.BankVerification;
import com.handmade.ecommerce.payment.service.BankAccountVerificationService;
import com.handmade.ecommerce.payment.service.PaymentValidationService;
import com.handmade.ecommerce.payment.service.factory.BankVerificationProviderFactory;
import com.handmade.ecommerce.payment.service.provider.BankVerificationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main implementation of bank account verification service
 * Orchestrates validation, verification, and database persistence
 * Uses Factory Pattern to delegate to appropriate provider
 */
@Service
public class BankAccountVerificationServiceImpl implements BankAccountVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountVerificationServiceImpl.class);

    @Autowired
    private BankVerificationProviderFactory providerFactory;

    @Autowired
    private BankVerificationRepository verificationRepository;

    @Autowired
    private PaymentValidationService paymentValidationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean validateFormat(BankAccountDetails bankDetails) {
        logger.info("Validating bank account format for country: {}", bankDetails.getCountryCode());

        try {
            // Validate account number format
            if (!paymentValidationService.validateAccountNumber(bankDetails.getAccountNumber())) {
                logger.warn("Invalid account number format");
                return false;
            }

            // Validate IFSC code for India
            if ("IN".equals(bankDetails.getCountryCode())) {
                if (bankDetails.getIfscCode() == null ||
                        !paymentValidationService.validateIfscCode(bankDetails.getIfscCode())) {
                    logger.warn("Invalid IFSC code format");
                    return false;
                }
            }

            // Validate routing number for USA
            if ("US".equals(bankDetails.getCountryCode())) {
                if (bankDetails.getRoutingNumber() == null ||
                        !bankDetails.getRoutingNumber().matches("^[0-9]{9}$")) {
                    logger.warn("Invalid routing number format");
                    return false;
                }
            }

            logger.info("Bank account format validation passed");
            return true;

        } catch (Exception e) {
            logger.error("Format validation failed", e);
            return false;
        }
    }

    @Override
    public BankVerificationResult verifyAccount(BankAccountDetails bankDetails) throws Exception {
        logger.info("Starting bank account verification for country: {}", bankDetails.getCountryCode());

        // Step 1: Format validation
        if (!validateFormat(bankDetails)) {
            throw new IllegalArgumentException("Bank account format validation failed");
        }

        // Step 2: Check if already verified
        Optional<BankVerification> existingVerification = verificationRepository
                .findFirstByAccountNumberOrderByCreatedAtDesc(bankDetails.getAccountNumber());

        if (existingVerification.isPresent() &&
                "VERIFIED".equals(existingVerification.get().getStatus())) {
            logger.info("Account already verified, returning cached result");
            return mapEntityToResult(existingVerification.get());
        }

        // Step 3: Select provider using Factory
        BankVerificationProvider provider = providerFactory.getProviderForCountry(bankDetails.getCountryCode());
        logger.info("Selected provider: {} for country: {}",
                provider.getProviderName(), bankDetails.getCountryCode());

        // Step 4: Perform verification
        BankVerificationResult result = provider.verify(bankDetails);

        // Step 5: Save to database
        BankVerification verification = mapResultToEntity(result, bankDetails);
        verification = verificationRepository.save(verification);
        result.setVerificationId(verification.getId());

        logger.info("Bank verification completed. Status: {}, Reference ID: {}",
                result.getStatus(), result.getReferenceId());

        return result;
    }

    @Override
    public BankVerificationResult verifyAccountWithProvider(BankAccountDetails bankDetails, String providerName)
            throws Exception {
        logger.info("Starting bank account verification with provider: {}", providerName);

        // Step 1: Format validation
        if (!validateFormat(bankDetails)) {
            throw new IllegalArgumentException("Bank account format validation failed");
        }

        // Step 2: Get specific provider from Factory
        BankVerificationProvider provider = providerFactory.getProvider(providerName);

        // Step 3: Check if provider supports country
        if (!provider.supportsCountry(bankDetails.getCountryCode())) {
            throw new IllegalArgumentException(
                    String.format("Provider %s does not support country %s",
                            providerName, bankDetails.getCountryCode()));
        }

        // Step 4: Perform verification
        BankVerificationResult result = provider.verify(bankDetails);

        // Step 5: Save to database
        BankVerification verification = mapResultToEntity(result, bankDetails);
        verification = verificationRepository.save(verification);
        result.setVerificationId(verification.getId());

        logger.info("Bank verification completed with provider {}. Status: {}",
                providerName, result.getStatus());

        return result;
    }

    @Override
    public BankVerificationResult getVerificationStatus(String referenceId) throws Exception {
        logger.info("Getting verification status for reference ID: {}", referenceId);

        // Step 1: Check database first
        Optional<BankVerification> verification = verificationRepository.findByReferenceId(referenceId);

        if (!verification.isPresent()) {
            throw new IllegalArgumentException("Verification not found for reference ID: " + referenceId);
        }

        BankVerification entity = verification.get();

        // Step 2: If status is PENDING, check with provider
        if ("PENDING".equals(entity.getStatus())) {
            BankVerificationProvider provider = providerFactory.getProvider(entity.getProviderName());
            BankVerificationResult result = provider.getVerificationStatus(referenceId);

            // Step 3: Update database with new status
            entity.setStatus(result.getStatus());
            entity.setVerifiedAt(result.getVerifiedAt());
            entity.setBankName(result.getBankName());
            verificationRepository.save(entity);

            return result;
        }

        // Return cached result
        return mapEntityToResult(entity);
    }

    @Override
    public List<BankVerificationResult> getVerificationHistory(String sellerId) {
        logger.info("Getting verification history for seller: {}", sellerId);

        List<BankVerification> verifications = verificationRepository.findBySellerId(sellerId);

        return verifications.stream()
                .map(this::mapEntityToResult)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountVerified(String accountNumber) {
        return verificationRepository.existsByAccountNumberAndStatus(accountNumber, "VERIFIED");
    }

    /**
     * Map verification result to entity for database persistence
     */
    private BankVerification mapResultToEntity(BankVerificationResult result, BankAccountDetails bankDetails) {
        BankVerification entity = new BankVerification();

        entity.setSellerId(bankDetails.getSellerId());
        entity.setAccountNumber(bankDetails.getAccountNumber());
        entity.setIfscCode(bankDetails.getIfscCode());
        entity.setRoutingNumber(bankDetails.getRoutingNumber());
        entity.setSwiftCode(bankDetails.getSwiftCode());
        entity.setAccountHolderName(bankDetails.getAccountHolderName());
        entity.setBankName(result.getBankName());
        entity.setCountryCode(bankDetails.getCountryCode());
        entity.setAccountType(bankDetails.getAccountType());
        entity.setProviderName(result.getProviderName());
        entity.setReferenceId(result.getReferenceId());
        entity.setStatus(result.getStatus());
        entity.setVerificationMethod(result.getVerificationMethod());
        entity.setVerifiedAt(result.getVerifiedAt());
        entity.setAmountDeposited(result.getAmountDeposited());
        entity.setErrorCode(result.getErrorCode());
        entity.setErrorMessage(result.getErrorMessage());

        // Serialize metadata to JSON
        try {
            if (result.getMetadata() != null && !result.getMetadata().isEmpty()) {
                entity.setMetadata(objectMapper.writeValueAsString(result.getMetadata()));
            }
        } catch (Exception e) {
            logger.error("Failed to serialize metadata", e);
        }

        return entity;
    }

    /**
     * Map entity to verification result
     */
    private BankVerificationResult mapEntityToResult(BankVerification entity) {
        BankVerificationResult result = new BankVerificationResult();

        result.setVerificationId(entity.getId());
        result.setStatus(entity.getStatus());
        result.setReferenceId(entity.getReferenceId());
        result.setProviderName(entity.getProviderName());
        result.setAccountNumber(entity.getAccountNumber());
        result.setAccountHolderName(entity.getAccountHolderName());
        result.setBankName(entity.getBankName());
        result.setVerifiedAt(entity.getVerifiedAt());
        result.setVerificationMethod(entity.getVerificationMethod());
        result.setAmountDeposited(entity.getAmountDeposited());
        result.setErrorCode(entity.getErrorCode());
        result.setErrorMessage(entity.getErrorMessage());

        // Deserialize metadata from JSON
        try {
            if (entity.getMetadata() != null && !entity.getMetadata().isEmpty()) {
                result.setMetadata(objectMapper.readValue(entity.getMetadata(), java.util.Map.class));
            }
        } catch (Exception e) {
            logger.error("Failed to deserialize metadata", e);
        }

        return result;
    }
}
