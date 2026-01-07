package com.handmade.ecommerce.seller.account.service.tax;

import com.handmade.ecommerce.seller.account.domain.valueobject.TaxInformation;
import com.handmade.ecommerce.seller.account.domain.valueobject.TaxVerificationResult;
import com.handmade.ecommerce.seller.account.dto.TaxDocumentRequest;
import com.handmade.ecommerce.seller.account.dto.TaxVerificationView;
import org.chenile.base.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for tax verification operations
 * Coordinates between domain, tax validation APIs, and database
 */
@Service
@Transactional
public class TaxVerificationService {

    private final TaxVerificationRepository repository;
    private final TaxValidationClient taxClient;

    public TaxVerificationService(
            TaxVerificationRepository repository,
            TaxValidationClient taxClient) {
        this.repository = repository;
        this.taxClient = taxClient;
    }

    /**
     * Create or get existing tax verification session
     */
    public TaxVerificationSession getOrCreateSession(String onboardingCaseId, String sellerEmail) {
        return repository.findByOnboardingCaseId(onboardingCaseId)
                .orElseGet(() -> {
                    TaxVerificationSession session = new TaxVerificationSession();
                    session.setOnboardingCaseId(onboardingCaseId);
                    session.setSellerEmail(sellerEmail);
                    return repository.save(session);
                });
    }

    /**
     * Submit tax documents and validate
     */
    public TaxVerificationView submitTaxDocuments(
            String onboardingCaseId,
            TaxDocumentRequest request) {

        // Get or create session
        TaxVerificationSession session = getOrCreateSession(onboardingCaseId, null);

        // Update tax information
        TaxInformation taxInfo = mapToTaxInformation(request);
        session.updateTaxInformation(taxInfo);

        // Update document URLs if provided
        if (request.getTaxCertificateUrl() != null || request.getGstCertificateUrl() != null) {
            session.updateDocuments(
                    request.getTaxCertificateUrl(),
                    request.getGstCertificateUrl());
        }

        repository.save(session);

        // Validate tax ID if ready
        if (session.isReadyForVerification()) {
            TaxValidationResponse validationResponse = taxClient.validateTaxId(
                    request.getTaxIdNumber(),
                    request.getTaxIdType(),
                    request.getTaxCountry(),
                    request.getBusinessName(),
                    request.getBusinessAddress());

            // Map validation response to result
            TaxVerificationResult result = mapToVerificationResult(
                    validationResponse,
                    request.getBusinessName(),
                    request.getBusinessAddress());

            // Record verification result
            session.recordVerificationResult(validationResponse.getStatus(), result);
            repository.save(session);
        }

        return mapToView(session);
    }

    /**
     * Get tax verification status
     */
    public TaxVerificationView getTaxVerificationStatus(String onboardingCaseId) {
        TaxVerificationSession session = repository.findByOnboardingCaseId(onboardingCaseId)
                .orElseThrow(() -> new NotFoundException("Tax verification session not found"));

        return mapToView(session);
    }

    // Mapping methods

    private TaxInformation mapToTaxInformation(TaxDocumentRequest request) {
        return new TaxInformation(
                request.getTaxIdType(),
                request.getTaxIdNumber(),
                request.getTaxCountry(),
                request.getBusinessName(),
                request.getBusinessAddress());
    }

    private TaxVerificationResult mapToVerificationResult(
            TaxValidationResponse response,
            String submittedName,
            String submittedAddress) {

        TaxVerificationResult result = new TaxVerificationResult();
        result.setTaxIdVerified(response.isValid());
        result.setBusinessNameMatches(response.isNameMatches());
        result.setAddressVerified(response.isAddressMatches());
        result.setVerificationStatus(response.getStatus());
        result.setFailureReason(response.getErrorMessage());
        result.setProviderMetadata(response.getProviderMetadata());

        return result;
    }

    private TaxVerificationView mapToView(TaxVerificationSession session) {
        TaxVerificationView view = new TaxVerificationView();
        view.setSessionId(session.getId());
        view.setOnboardingCaseId(session.getOnboardingCaseId());
        view.setProviderStatus(session.getProviderStatus());
        view.setVerified(session.isVerified());
        view.setFailed(session.isFailed());

        if (session.getTaxInformation() != null) {
            view.setTaxIdType(session.getTaxInformation().getTaxIdType());
            view.setMaskedTaxId(maskTaxId(session.getTaxInformation().getTaxIdNumber()));
        }

        if (session.getResult() != null) {
            view.setFailureReason(session.getResult().getFailureReason());
            view.setVerificationStatus(session.getResult().getVerificationStatus());
        }

        view.setCreatedAt(session.getCreatedAt());
        view.setSubmittedAt(session.getSubmittedAt());
        view.setVerifiedAt(session.getVerifiedAt());

        return view;
    }

    /**
     * Mask tax ID for security
     */
    private String maskTaxId(String taxId) {
        if (taxId == null || taxId.length() < 4) {
            return "***";
        }
        int visibleChars = 3;
        String prefix = taxId.substring(0, visibleChars);
        String suffix = taxId.substring(taxId.length() - visibleChars);
        int maskedLength = taxId.length() - (2 * visibleChars);
        String masked = "*".repeat(Math.max(0, maskedLength));
        return prefix + masked + suffix;
    }
}
