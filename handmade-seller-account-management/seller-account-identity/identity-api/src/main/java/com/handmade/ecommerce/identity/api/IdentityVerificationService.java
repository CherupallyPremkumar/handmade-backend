package com.handmade.ecommerce.identity.api;

import com.handmade.ecommerce.identity.dto.IdentityDocumentRequest;
import com.handmade.ecommerce.identity.dto.IdentityVerificationView;
import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;

/**
 * Service interface for identity verification operations.
 * Coordinates between domain, Stripe Identity, and STM.
 */
public interface IdentityVerificationService {

    /**
     * Get or create identity verification session for an onboarding case.
     *
     * @param onboardingCaseId The onboarding case ID
     * @param sellerEmail The seller's email
     * @return The identity verification session
     */
    IdentityVerificationSession getOrCreateSession(String onboardingCaseId, String sellerEmail);

    /**
     * Submit identity documents and create Stripe verification session.
     *
     * @param onboardingCaseId The onboarding case ID
     * @param request The identity document request
     * @return View object with verification status and external session details
     */
    IdentityVerificationView submitIdentityDocuments(String onboardingCaseId, IdentityDocumentRequest request);

    /**
     * Get identity verification status for an onboarding case.
     *
     * @param onboardingCaseId The onboarding case ID
     * @return View object with current verification status
     */
    IdentityVerificationView getVerificationStatus(String onboardingCaseId);

    /**
     * Find identity verification session by external session ID.
     * Used for webhook processing.
     *
     * @param externalSessionId The external session ID
     * @return The identity verification session
     */
    IdentityVerificationSession findByExternalSessionId(String externalSessionId);

    /**
     * Create an external identity verification session.
     * Returns client secret for frontend to open provider UI.
     * Does NOT trigger STM transition - webhook will do that.
     *
     * @param onboardingCaseId The onboarding case ID
     * @return View object with external client secret
     */
    IdentityVerificationView createSession(String onboardingCaseId);
}
