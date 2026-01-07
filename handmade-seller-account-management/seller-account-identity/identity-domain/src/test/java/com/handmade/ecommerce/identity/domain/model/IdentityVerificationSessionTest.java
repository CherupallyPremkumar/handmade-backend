package com.handmade.ecommerce.identity.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for IdentityVerificationSession entity
 */
class IdentityVerificationSessionTest {

    private IdentityVerificationSession session;

    @BeforeEach
    void setUp() {
        session = new IdentityVerificationSession();
        session.setOnboardingCaseId("case123");
        session.setSellerEmail("seller@example.com");
    }

    @Test
    void constructor_InitializesDefaultValues() {
        // Given
        IdentityVerificationSession newSession = new IdentityVerificationSession();

        // Then
        assertNotNull(newSession.getCreatedAt());
        assertEquals("pending", newSession.getExternalStatus());
    }

    @Test
    void updatePersonalIdentity_UpdatesIdentityAndSubmittedAt() {
        // Given
        PersonalIdentity identity = new PersonalIdentity(
                "John Doe",
                LocalDate.of(1990, 1, 1),
                "123456789",
                "PASSPORT",
                "US",
                "US");

        // When
        session.updatePersonalIdentity(identity);

        // Then
        assertNotNull(session.getPersonalIdentity());
        assertEquals("John Doe", session.getPersonalIdentity().getLegalName());
        assertNotNull(session.getSubmittedAt());
    }

    @Test
    void updatePersonalIdentity_CalledMultipleTimes_DoesNotUpdateSubmittedAt() {
        // Given
        PersonalIdentity identity1 = new PersonalIdentity(
                "John Doe", LocalDate.of(1990, 1, 1), "123", "PASSPORT", "US", "US");
        PersonalIdentity identity2 = new PersonalIdentity(
                "Jane Doe", LocalDate.of(1991, 1, 1), "456", "PASSPORT", "US", "US");

        // When
        session.updatePersonalIdentity(identity1);
        Instant firstSubmittedAt = session.getSubmittedAt();

        // Simulate some time passed (not strictly needed but good for clarity)
        session.updatePersonalIdentity(identity2);
        Instant secondSubmittedAt = session.getSubmittedAt();

        // Then
        assertEquals(firstSubmittedAt, secondSubmittedAt);
        assertEquals("Jane Doe", session.getPersonalIdentity().getLegalName());
    }

    @Test
    void recordStripeSession_UpdatesStripeFields() {
        // When
        session.recordExternalVerificationSession("session123", "secret_abc", "https://stripe.com");

        // Then
        assertEquals("session123", session.getExternalSessionId());
        assertEquals("secret_abc", session.getExternalClientSecret());
        assertEquals("https://stripe.com", session.getExternalUrl());
        assertEquals("processing", session.getExternalStatus());
    }

    @Test
    void recordStripeSession_WhenAlreadyCreated_ThrowsException() {
        // Given
        session.recordExternalVerificationSession("session123", "secret_abc", "https://stripe.com");

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            session.recordExternalVerificationSession("session456", "secret_def", "https://stripe.com");
        });
    }

    @Test
    void updateFromStripeWebhook_UpdatesStatusAndResult() {
        // Given
        VerificationResult result = new VerificationResult();
        result.setIdentityVerified(true);
        result.setDocumentVerified(true);

        // When
        session.updateFromExternalWebhook("verified", result);

        // Then
        assertEquals("verified", session.getExternalStatus());
        assertNotNull(session.getResult());
        assertTrue(session.getResult().isIdentityVerified());
        assertNotNull(session.getVerifiedAt());
    }

    @Test
    void updateFromStripeWebhook_WithNonVerifiedStatus_DoesNotSetVerifiedAt() {
        // Given
        VerificationResult result = new VerificationResult();
        result.setIdentityVerified(false);

        // When
        session.updateFromExternalWebhook("requires_input", result);

        // Then
        assertEquals("requires_input", session.getExternalStatus());
        assertNull(session.getVerifiedAt());
    }

    @Test
    void isReadyForStripeSession_WhenIdentitySet_ReturnsTrue() {
        // Given
        PersonalIdentity identity = new PersonalIdentity(
                "John Doe", LocalDate.of(1990, 1, 1), "123", "PASSPORT", "US", "US");
        session.updatePersonalIdentity(identity);

        // When
        boolean result = session.isReadyForExternalSession();

        // Then
        assertTrue(result);
    }

    @Test
    void isReadyForStripeSession_WhenIdentityNotSet_ReturnsFalse() {
        // When
        boolean result = session.isReadyForExternalSession();

        // Then
        assertFalse(result);
    }

    @Test
    void isReadyForStripeSession_WhenStripeSessionAlreadyCreated_ReturnsFalse() {
        // Given
        PersonalIdentity identity = new PersonalIdentity(
                "John Doe", LocalDate.of(1990, 1, 1), "123", "PASSPORT", "US", "US");
        session.updatePersonalIdentity(identity);
        session.recordExternalVerificationSession("session123", "secret", "url");

        // When
        boolean result = session.isReadyForExternalSession();

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
    void isVerified_WhenStatusIsNotVerified_ReturnsFalse() {
        // Given
        session.setExternalStatus("processing");

        // When
        boolean result = session.isVerified();

        // Then
        assertFalse(result);
    }

    @Test
    void isFailed_WhenStatusIsCanceled_ReturnsTrue() {
        // Given
        session.setExternalStatus("canceled");

        // When
        boolean result = session.isFailed();

        // Then
        assertTrue(result);
    }

    @Test
    void isFailed_WhenResultShowsNotVerified_ReturnsTrue() {
        // Given
        VerificationResult result = new VerificationResult();
        result.setIdentityVerified(false);
        session.setResult(result);

        // When
        boolean resultFlag = session.isFailed();

        // Then
        assertTrue(resultFlag);
    }

    @Test
    void isFailed_WhenVerified_ReturnsFalse() {
        // Given
        session.setExternalStatus("verified");
        VerificationResult result = new VerificationResult();
        result.setIdentityVerified(true);
        session.setResult(result);

        // When
        boolean resultFlag = session.isFailed();

        // Then
        assertFalse(resultFlag);
    }

    @Test
    void updateFromStripeWebhook_WithCanceledStatus_SetsResultAndStatus() {
        // Given
        VerificationResult result = new VerificationResult();
        result.setIdentityVerified(false);
        result.setFailureReason("User canceled");

        // When
        session.updateFromExternalWebhook("canceled", result);

        // Then
        assertEquals("canceled", session.getExternalStatus());
        assertFalse(session.getResult().isIdentityVerified());
        assertNull(session.getVerifiedAt());
    }

    @Test
    void isReadyForStripeSession_WhenAlreadyVerified_ReturnsFalse() {
        // Given
        PersonalIdentity identity = new PersonalIdentity(
                "John Doe", LocalDate.of(1990, 1, 1), "123", "PASSPORT", "US", "US");
        session.updatePersonalIdentity(identity);
        session.setExternalStatus("verified");

        // When
        boolean result = session.isReadyForExternalSession();

        // Then
        assertFalse(result);
    }
}
