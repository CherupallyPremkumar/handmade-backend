package com.handmade.ecommerce.onboarding.service.bank;

import com.handmade.ecommerce.onboarding.domain.valueobject.BankAccountInfo;
import com.handmade.ecommerce.onboarding.domain.valueobject.BankVerificationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BankVerificationSession entity
 */
class BankVerificationSessionTest {

    private BankVerificationSession session;

    @BeforeEach
    void setUp() {
        session = new BankVerificationSession();
        session.setOnboardingCaseId("case123");
        session.setSellerEmail("seller@example.com");
    }

    @Test
    void constructor_InitializesDefaultValues() {
        // Given
        BankVerificationSession newSession = new BankVerificationSession();

        // Then
        assertNotNull(newSession.getCreatedAt());
        assertEquals("new", newSession.getExternalStatus());
        assertEquals(0, newSession.getVerificationAttempts());
        assertEquals(3, newSession.getMaxVerificationAttempts());
    }

    @Test
    void updateBankAccountInfo_UpdatesInfoAndSubmittedAt() {
        // Given
        BankAccountInfo bankInfo = new BankAccountInfo(
                "John Doe", "CHECKING", "110000000", "6789",
                "Chase Bank", "US", "USD");

        // When
        session.updateBankAccountInfo(bankInfo);

        // Then
        assertNotNull(session.getBankAccountInfo());
        assertEquals("John Doe", session.getBankAccountInfo().getAccountHolderName());
        assertNotNull(session.getSubmittedAt());
    }

    @Test
    void recordStripeAccount_UpdatesStripeFields() {
        // When
        session.recordExternalAccount("acct_123", "ba_456");

        // Then
        assertEquals("acct_123", session.getExternalAccountId());
        assertEquals("ba_456", session.getExternalBankAccountId());
    }

    @Test
    void recordMicroDeposits_UpdatesAmountsAndMethod() {
        // When
        session.recordMicroDeposits(32, 45);

        // Then
        assertEquals(32, session.getMicroDeposit1());
        assertEquals(45, session.getMicroDeposit2());
        assertEquals("MICRO_DEPOSIT", session.getVerificationMethod());
    }

    @Test
    void verifyMicroDeposits_WithCorrectAmounts_ReturnsTrue() {
        // Given
        session.recordMicroDeposits(32, 45);

        // When
        boolean result = session.verifyMicroDeposits(32, 45);

        // Then
        assertTrue(result);
        assertEquals("verified", session.getExternalStatus());
        assertNotNull(session.getVerifiedAt());
        assertEquals(1, session.getVerificationAttempts());
    }

    @Test
    void verifyMicroDeposits_WithIncorrectAmounts_ReturnsFalse() {
        // Given
        session.recordMicroDeposits(32, 45);

        // When
        boolean result = session.verifyMicroDeposits(10, 20);

        // Then
        assertFalse(result);
        assertNotEquals("verified", session.getExternalStatus());
        assertNull(session.getVerifiedAt());
        assertEquals(1, session.getVerificationAttempts());
    }

    @Test
    void verifyMicroDeposits_ExceedsMaxAttempts_ReturnsFalseAndMarksAsFailed() {
        // Given
        session.recordMicroDeposits(32, 45);

        // When
        session.verifyMicroDeposits(10, 20); // Attempt 1
        session.verifyMicroDeposits(15, 25); // Attempt 2
        boolean result = session.verifyMicroDeposits(20, 30); // Attempt 3

        // Then
        assertFalse(result);
        assertEquals("verification_failed", session.getExternalStatus());
        assertEquals(3, session.getVerificationAttempts());
    }

    @Test
    void verifyMicroDeposits_AfterMaxAttempts_ReturnsFalse() {
        // Given
        session.recordMicroDeposits(32, 45);
        session.verifyMicroDeposits(10, 20);
        session.verifyMicroDeposits(15, 25);
        session.verifyMicroDeposits(20, 30);

        // When
        boolean result = session.verifyMicroDeposits(32, 45); // Correct amounts but too late

        // Then
        assertFalse(result);
        assertEquals(3, session.getVerificationAttempts());
    }

    @Test
    void recordVerificationResult_UpdatesStatusAndResult() {
        // Given
        BankVerificationResult result = new BankVerificationResult();
        result.setAccountVerified(true);
        result.setRoutingNumberValid(true);

        // When
        session.recordVerificationResult("verified", result);

        // Then
        assertEquals("verified", session.getExternalStatus());
        assertNotNull(session.getResult());
        assertTrue(session.getResult().isAccountVerified());
        assertNotNull(session.getVerifiedAt());
    }

    @Test
    void isReadyForVerification_WhenAllFieldsSet_ReturnsTrue() {
        // Given
        BankAccountInfo bankInfo = new BankAccountInfo(
                "John Doe", "CHECKING", "110000000", "6789",
                "Chase Bank", "US", "USD");
        session.updateBankAccountInfo(bankInfo);
        session.recordExternalAccount("acct_123", "ba_456");

        // When
        boolean result = session.isReadyForVerification();

        // Then
        assertTrue(result);
    }

    @Test
    void isReadyForVerification_WhenBankInfoMissing_ReturnsFalse() {
        // Given
        session.recordExternalAccount("acct_123", "ba_456");

        // When
        boolean result = session.isReadyForVerification();

        // Then
        assertFalse(result);
    }

    @Test
    void isVerified_WhenStatusIsVerified_ReturnsTrue() {
        // Given
        session.setExternalStatus("verified");

        // When
        boolean result = session.isVerified();

        // Then
        assertTrue(result);
    }

    @Test
    void isFailed_WhenStatusIsVerificationFailed_ReturnsTrue() {
        // Given
        session.setExternalStatus("verification_failed");

        // When
        boolean result = session.isFailed();

        // Then
        assertTrue(result);
    }

    @Test
    void hasMicroDeposits_WhenDepositsRecorded_ReturnsTrue() {
        // Given
        session.recordMicroDeposits(32, 45);

        // When
        boolean result = session.hasMicroDeposits();

        // Then
        assertTrue(result);
    }

    @Test
    void hasMicroDeposits_WhenNoDeposits_ReturnsFalse() {
        // When
        boolean result = session.hasMicroDeposits();

        // Then
        assertFalse(result);
    }

    @Test
    void getRemainingAttempts_ReturnsCorrectCount() {
        // Given
        session.recordMicroDeposits(32, 45);

        // When
        int remaining1 = session.getRemainingAttempts();
        session.verifyMicroDeposits(10, 20);
        int remaining2 = session.getRemainingAttempts();
        session.verifyMicroDeposits(15, 25);
        int remaining3 = session.getRemainingAttempts();

        // Then
        assertEquals(3, remaining1);
        assertEquals(2, remaining2);
        assertEquals(1, remaining3);
    }
}
