package com.handmade.ecommerce.seller.account.service.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Bank Verification Sessions
 */
@Repository
public interface BankVerificationRepository extends JpaRepository<BankVerificationSession, String> {

    /**
     * Find by onboarding case ID
     */
    Optional<BankVerificationSession> findByOnboardingCaseId(String onboardingCaseId);

    /**
     * Find by external provider account ID
     */
    Optional<BankVerificationSession> findByExternalAccountId(String externalAccountId);

    /**
     * Check if session exists for onboarding case
     */
    boolean existsByOnboardingCaseId(String onboardingCaseId);
}
