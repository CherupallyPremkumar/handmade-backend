package com.handmade.ecommerce.seller.account.service.tax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Tax Verification Sessions
 */
@Repository
public interface TaxVerificationRepository extends JpaRepository<TaxVerificationSession, String> {

    /**
     * Find by onboarding case ID
     */
    Optional<TaxVerificationSession> findByOnboardingCaseId(String onboardingCaseId);

    /**
     * Find by provider session ID
     */
    Optional<TaxVerificationSession> findByProviderSessionId(String providerSessionId);

    /**
     * Check if session exists for onboarding case
     */
    boolean existsByOnboardingCaseId(String onboardingCaseId);
}
