package com.handmade.ecommerce.identity.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;

import java.util.Optional;

/**
 * Repository for Identity Verification Sessions
 */
@Repository
public interface IdentityVerificationRepository extends JpaRepository<IdentityVerificationSession, String> {

    /**
     * Find by onboarding case ID
     */
    Optional<IdentityVerificationSession> findByOnboardingCaseId(String onboardingCaseId);

    /**
     * Find by external provider session ID (for webhook processing)
     */
    Optional<IdentityVerificationSession> findByExternalSessionId(String externalSessionId);

    /**
     * Find by external provider verification ID
     */
    Optional<IdentityVerificationSession> findByExternalVerificationId(String externalVerificationId);

    /**
     * Check if session exists for onboarding case
     */
    boolean existsByOnboardingCaseId(String onboardingCaseId);
}
