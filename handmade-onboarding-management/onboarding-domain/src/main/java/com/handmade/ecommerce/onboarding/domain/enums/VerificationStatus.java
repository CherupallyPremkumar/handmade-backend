package com.handmade.ecommerce.onboarding.domain.enums;

/**
 * Verification status for identity, tax, and bank verification steps
 */
public enum VerificationStatus {

    /**
     * Not started yet
     */
    PENDING,

    /**
     * In progress - documents submitted, waiting for verification
     */
    IN_PROGRESS,

    /**
     * Verified successfully
     */
    VERIFIED,

    /**
     * Verification failed
     */
    FAILED,

    /**
     * Verification rejected
     */
    REJECTED,

    /**
     * Requires manual review
     */
    MANUAL_REVIEW,

    /**
     * Not required for this seller type
     */
    NOT_REQUIRED
}
