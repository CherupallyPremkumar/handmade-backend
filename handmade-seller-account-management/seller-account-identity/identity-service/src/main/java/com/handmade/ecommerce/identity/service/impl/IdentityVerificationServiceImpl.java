package com.handmade.ecommerce.identity.service.impl;

import com.handmade.ecommerce.identity.api.IdentityVerificationService;
import com.handmade.ecommerce.identity.dto.IdentityDocumentRequest;
import com.handmade.ecommerce.identity.dto.IdentityVerificationView;
import com.handmade.ecommerce.identity.domain.gateway.ExternalIdentityGateway;
import com.handmade.ecommerce.identity.domain.model.ExternalVerificationSession;
import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;
import com.handmade.ecommerce.identity.domain.model.PersonalIdentity;
import com.handmade.ecommerce.identity.infrastructure.repository.IdentityVerificationRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for identity verification operations.
 * Coordinates between domain, Stripe, and STM.
 */
@Service
@Transactional
public class IdentityVerificationServiceImpl implements IdentityVerificationService {

    private final IdentityVerificationRepository repository;
    private final ExternalIdentityGateway identityGateway;

    public IdentityVerificationServiceImpl(
            IdentityVerificationRepository repository,
            ExternalIdentityGateway identityGateway) {
        this.repository = repository;
        this.identityGateway = identityGateway;
    }

    /**
     * Create or get existing identity verification session
     */
    @Override
    public IdentityVerificationSession getOrCreateSession(String onboardingCaseId, String sellerEmail) {
        return repository.findByOnboardingCaseId(onboardingCaseId)
                .orElseGet(() -> {
                    IdentityVerificationSession session = new IdentityVerificationSession();
                    session.setOnboardingCaseId(onboardingCaseId);
                    session.setSellerEmail(sellerEmail);
                    return repository.save(session);
                });
    }

    /**
     * Submit identity documents and create Stripe session
     */
    @Override
    public IdentityVerificationView submitIdentityDocuments(
            String onboardingCaseId,
            IdentityDocumentRequest request) {

        // Get or create session
        IdentityVerificationSession session = getOrCreateSession(onboardingCaseId, null);

        // Update personal identity
        PersonalIdentity identity = mapToPersonalIdentity(request);
        session.updatePersonalIdentity(identity);
        repository.save(session);

        // Create external verification session
        if (session.isReadyForExternalSession() && identityGateway.isConfigured()) {
            ExternalVerificationSession externalSession = identityGateway.createVerificationSession(session);
            if (externalSession != null) {
                session.recordExternalVerificationSession(
                        externalSession.getSessionId(),
                        externalSession.getClientSecret(),
                        externalSession.getUrl());
                repository.save(session);
            }
        }

        return mapToView(session);
    }

    /**
     * Get identity verification status
     */
    @Override
    public IdentityVerificationView getVerificationStatus(String onboardingCaseId) {
        IdentityVerificationSession session = repository.findByOnboardingCaseId(onboardingCaseId)
                .orElseThrow(() -> new NotFoundException("Identity verification session not found"));

        return mapToView(session);
    }

    /**
     * Find session by external session ID (for webhook processing)
     */
    @Override
    public IdentityVerificationSession findByExternalSessionId(String externalSessionId) {
        return repository.findByExternalSessionId(externalSessionId)
                .orElseThrow(() -> new NotFoundException("Session not found for external ID: " + externalSessionId));
    }

    // Mapping methods

    private PersonalIdentity mapToPersonalIdentity(IdentityDocumentRequest request) {
        return new PersonalIdentity(
                request.getLegalName(),
                request.getDateOfBirth(),
                request.getNationalId(),
                request.getIdType(),
                request.getIdCountry(),
                request.getNationality());
    }

    @Override
    public IdentityVerificationView createSession(String onboardingCaseId) {
        // Get or create session
        IdentityVerificationSession session = getOrCreateSession(onboardingCaseId, null);

        // Create external verification session if not already existing
        if (session.isReadyForExternalSession() && session.getExternalSessionId() == null && identityGateway.isConfigured()) {
            ExternalVerificationSession externalSession = identityGateway.createVerificationSession(session);
            if (externalSession != null) {
                session.recordExternalVerificationSession(
                        externalSession.getSessionId(),
                        externalSession.getClientSecret(),
                        externalSession.getUrl());
                repository.save(session);
            }
        }

        return mapToView(session);
    }

    private IdentityVerificationView mapToView(IdentityVerificationSession session) {
        IdentityVerificationView view = new IdentityVerificationView();
        view.setSessionId(session.getId());
        view.setOnboardingCaseId(session.getOnboardingCaseId());
        view.setExternalClientSecret(session.getExternalClientSecret());
        view.setExternalUrl(session.getExternalUrl());
        view.setExternalStatus(session.getExternalStatus());
        view.setVerified(session.isVerified());
        view.setFailed(session.isFailed());

        if (session.getResult() != null) {
            view.setFailureReason(session.getResult().getFailureReason());
            view.setRiskScore(session.getResult().getRiskScore());
        }

        view.setCreatedAt(session.getCreatedAt());
        view.setSubmittedAt(session.getSubmittedAt());
        view.setVerifiedAt(session.getVerifiedAt());

        return view;
    }
}
