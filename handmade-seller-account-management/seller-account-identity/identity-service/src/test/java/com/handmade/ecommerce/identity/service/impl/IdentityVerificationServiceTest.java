package com.handmade.ecommerce.identity.service.impl;

import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;
import com.handmade.ecommerce.identity.domain.model.PersonalIdentity;
import com.handmade.ecommerce.identity.dto.IdentityDocumentRequest;
import com.handmade.ecommerce.identity.dto.IdentityVerificationView;
import com.handmade.ecommerce.identity.domain.model.ExternalVerificationSession;
import com.handmade.ecommerce.identity.infrastructure.repository.IdentityVerificationRepository;
import com.handmade.ecommerce.identity.domain.gateway.ExternalIdentityGateway;
import org.chenile.base.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for IdentityVerificationServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class IdentityVerificationServiceTest {

    @Mock
    private IdentityVerificationRepository repository;

    @Mock
    private ExternalIdentityGateway identityGateway;

    @InjectMocks
    private IdentityVerificationServiceImpl service;

    private IdentityDocumentRequest validRequest;
    private IdentityVerificationSession existingSession;

    @BeforeEach
    void setUp() {
        // Setup valid request
        validRequest = new IdentityDocumentRequest();
        validRequest.setLegalName("John Doe");
        validRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        validRequest.setNationalId("123456789");
        validRequest.setIdType("PASSPORT");
        validRequest.setIdCountry("US");
        validRequest.setNationality("US");

        // Setup existing session
        existingSession = new IdentityVerificationSession();
        existingSession.setOnboardingCaseId("case123");
        existingSession.setSellerEmail("seller@example.com");
    }

    @Test
    void getOrCreateSession_WhenSessionExists_ReturnsExistingSession() {
        // Given
        when(repository.findByOnboardingCaseId("case123"))
                .thenReturn(Optional.of(existingSession));

        // When
        IdentityVerificationSession result = service.getOrCreateSession("case123", "seller@example.com");

        // Then
        assertNotNull(result);
        assertEquals("case123", result.getOnboardingCaseId());
        verify(repository, never()).save(any());
    }

    @Test
    void getOrCreateSession_WhenSessionDoesNotExist_CreatesNewSession() {
        // Given
        when(repository.findByOnboardingCaseId("case123"))
                .thenReturn(Optional.empty());
        when(repository.save(any(IdentityVerificationSession.class)))
                .thenAnswer(invocation -> {
                    IdentityVerificationSession session = invocation.getArgument(0);
                    return session;
                });

        // When
        IdentityVerificationSession result = service.getOrCreateSession("case123", "seller@example.com");

        // Then
        assertNotNull(result);
        assertEquals("case123", result.getOnboardingCaseId());
        assertEquals("seller@example.com", result.getSellerEmail());
        verify(repository, times(1)).save(any());
    }

    @Test
    void submitIdentityDocuments_WithNewSession_CreatesSessionAndUpdatesIdentity() {
        // Given
        when(repository.findByOnboardingCaseId("case123"))
                .thenReturn(Optional.empty());
        when(repository.save(any(IdentityVerificationSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(identityGateway.isConfigured()).thenReturn(false);

        // When
        IdentityVerificationView result = service.submitIdentityDocuments("case123", validRequest);

        // Then
        assertNotNull(result);
        assertEquals("case123", result.getOnboardingCaseId());
        assertFalse(result.isVerified());

        ArgumentCaptor<IdentityVerificationSession> captor = ArgumentCaptor.forClass(IdentityVerificationSession.class);
        verify(repository, atLeastOnce()).save(captor.capture());

        IdentityVerificationSession savedSession = captor.getValue();
        assertNotNull(savedSession.getPersonalIdentity());
        assertEquals("John Doe", savedSession.getPersonalIdentity().getLegalName());
    }

    @Test
    void submitIdentityDocuments_WithStripeConfigured_CreatesStripeSession() {
        // Given
        when(repository.findByOnboardingCaseId("case123"))
                .thenReturn(Optional.of(existingSession));
        when(repository.save(any(IdentityVerificationSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(identityGateway.isConfigured()).thenReturn(true);

        ExternalVerificationSession response = new ExternalVerificationSession(
                "stripe_session_123",
                "secret_abc",
                "https://verify.stripe.com/start/stripe_session_123");
        when(identityGateway.createVerificationSession(any()))
                .thenReturn(response);

        // When
        IdentityVerificationView result = service.submitIdentityDocuments("case123", validRequest);

        // Then
        assertNotNull(result);
        assertEquals("secret_abc", result.getExternalClientSecret());
        assertEquals("https://verify.stripe.com/start/stripe_session_123", result.getExternalUrl());
        verify(identityGateway, times(1)).createVerificationSession(any());
    }

    @Test
    void getIdentityVerificationStatus_WhenSessionExists_ReturnsView() {
        // Given
        existingSession.setExternalStatus("verified");
        when(repository.findByOnboardingCaseId("case123"))
                .thenReturn(Optional.of(existingSession));

        // When
        IdentityVerificationView result = service.getVerificationStatus("case123");

        // Then
        assertNotNull(result);
        assertEquals("case123", result.getOnboardingCaseId());
        assertEquals("verified", result.getExternalStatus());
    }

    @Test
    void getIdentityVerificationStatus_WhenSessionDoesNotExist_ThrowsNotFoundException() {
        // Given
        when(repository.findByOnboardingCaseId("case123"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            service.getVerificationStatus("case123");
        });
    }

    @Test
    void findByStripeSessionId_WhenSessionExists_ReturnsSession() {
        // Given
        existingSession.setExternalSessionId("stripe_session_123");
        when(repository.findByExternalSessionId("stripe_session_123"))
                .thenReturn(Optional.of(existingSession));

        // When
        IdentityVerificationSession result = service.findByExternalSessionId("stripe_session_123");

        // Then
        assertNotNull(result);
        assertEquals("stripe_session_123", result.getExternalSessionId());
    }

    @Test
    void findByStripeSessionId_WhenSessionDoesNotExist_ThrowsNotFoundException() {
        // Given
        when(repository.findByExternalSessionId("stripe_session_123"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            service.findByExternalSessionId("stripe_session_123");
        });
    }

    @Test
    void submitIdentityDocuments_MapsPersonalIdentityCorrectly() {
        // Given
        when(repository.findByOnboardingCaseId("case123"))
                .thenReturn(Optional.empty());
        when(repository.save(any(IdentityVerificationSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(identityGateway.isConfigured()).thenReturn(false);

        // When
        service.submitIdentityDocuments("case123", validRequest);

        // Then
        ArgumentCaptor<IdentityVerificationSession> captor = ArgumentCaptor.forClass(IdentityVerificationSession.class);
        verify(repository, atLeastOnce()).save(captor.capture());

        PersonalIdentity identity = captor.getValue().getPersonalIdentity();
        assertNotNull(identity);
        assertEquals("John Doe", identity.getLegalName());
        assertEquals(LocalDate.of(1990, 1, 1), identity.getDateOfBirth());
        assertEquals("123456789", identity.getNationalId());
        assertEquals("PASSPORT", identity.getIdType());
        assertEquals("US", identity.getIdCountry());
        assertEquals("US", identity.getNationality());
    }
}
