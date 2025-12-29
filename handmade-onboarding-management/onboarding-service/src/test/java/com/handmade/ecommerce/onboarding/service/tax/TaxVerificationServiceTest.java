package com.handmade.ecommerce.onboarding.service.tax;

import com.handmade.ecommerce.onboarding.domain.valueobject.TaxInformation;
import com.handmade.ecommerce.onboarding.domain.valueobject.TaxVerificationResult;
import com.handmade.ecommerce.onboarding.dto.TaxDocumentRequest;
import com.handmade.ecommerce.onboarding.dto.TaxVerificationView;
import org.chenile.base.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaxVerificationService
 */
@ExtendWith(MockitoExtension.class)
class TaxVerificationServiceTest {

        @Mock
        private TaxVerificationRepository repository;

        @Mock
        private TaxValidationClient taxClient;

        @InjectMocks
        private TaxVerificationService service;

        private TaxDocumentRequest validRequest;
        private TaxVerificationSession existingSession;

        @BeforeEach
        void setUp() {
                // Setup valid request
                validRequest = new TaxDocumentRequest();
                validRequest.setTaxIdType("GST");
                validRequest.setTaxIdNumber("27AAPFU0939F1ZV");
                validRequest.setTaxCountry("IN");
                validRequest.setBusinessName("Acme Corp");
                validRequest.setBusinessAddress("123 Main St, Mumbai");

                // Setup existing session
                existingSession = new TaxVerificationSession();
                existingSession.setId("session123");
                existingSession.setOnboardingCaseId("case123");
                existingSession.setSellerEmail("seller@example.com");
        }

        @Test
        void getOrCreateSession_WhenSessionExists_ReturnsExistingSession() {
                // Given
                when(repository.findByOnboardingCaseId("case123"))
                                .thenReturn(Optional.of(existingSession));

                // When
                TaxVerificationSession result = service.getOrCreateSession("case123", "seller@example.com");

                // Then
                assertNotNull(result);
                assertEquals("session123", result.getId());
                verify(repository, never()).save(any());
        }

        @Test
        void getOrCreateSession_WhenSessionDoesNotExist_CreatesNewSession() {
                // Given
                when(repository.findByOnboardingCaseId("case123"))
                                .thenReturn(Optional.empty());
                when(repository.save(any(TaxVerificationSession.class)))
                                .thenAnswer(invocation -> {
                                        TaxVerificationSession session = invocation.getArgument(0);
                                        session.setId("newSession123");
                                        return session;
                                });

                // When
                TaxVerificationSession result = service.getOrCreateSession("case123", "seller@example.com");

                // Then
                assertNotNull(result);
                assertEquals("newSession123", result.getId());
                assertEquals("case123", result.getOnboardingCaseId());
                verify(repository, times(1)).save(any());
        }

        @Test
        void submitTaxDocuments_ValidatesAndStoresResult() {
                // Given
                when(repository.findByOnboardingCaseId("case123"))
                                .thenReturn(Optional.empty());
                when(repository.save(any(TaxVerificationSession.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                TaxValidationResponse validationResponse = new TaxValidationResponse();
                validationResponse.setValid(true);
                validationResponse.setStatus("ACTIVE");
                validationResponse.setBusinessName("Acme Corp");
                validationResponse.setNameMatches(true);

                when(taxClient.validateTaxId(any(), any(), any(), any(), any()))
                                .thenReturn(validationResponse);

                // When
                TaxVerificationView result = service.submitTaxDocuments("case123", validRequest);

                // Then
                assertNotNull(result);
                assertEquals("case123", result.getOnboardingCaseId());
                assertTrue(result.isVerified());
                assertEquals("ACTIVE", result.getVerificationStatus());

                verify(taxClient, times(1)).validateTaxId(
                                "27AAPFU0939F1ZV", "GST", "IN", "Acme Corp", "123 Main St, Mumbai");
        }

        @Test
        void submitTaxDocuments_MapsTaxInformationCorrectly() {
                // Given
                when(repository.findByOnboardingCaseId("case123"))
                                .thenReturn(Optional.empty());
                when(repository.save(any(TaxVerificationSession.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                TaxValidationResponse validationResponse = new TaxValidationResponse();
                validationResponse.setValid(true);
                validationResponse.setStatus("ACTIVE");

                when(taxClient.validateTaxId(any(), any(), any(), any(), any()))
                                .thenReturn(validationResponse);

                // When
                service.submitTaxDocuments("case123", validRequest);

                // Then
                ArgumentCaptor<TaxVerificationSession> captor = ArgumentCaptor.forClass(TaxVerificationSession.class);
                verify(repository, atLeastOnce()).save(captor.capture());

                TaxInformation taxInfo = captor.getValue().getTaxInformation();
                assertNotNull(taxInfo);
                assertEquals("GST", taxInfo.getTaxIdType());
                assertEquals("27AAPFU0939F1ZV", taxInfo.getTaxIdNumber());
                assertEquals("IN", taxInfo.getTaxCountry());
                assertEquals("Acme Corp", taxInfo.getBusinessName());
        }

        @Test
        void getTaxVerificationStatus_WhenSessionExists_ReturnsView() {
                // Given
                existingSession.setProviderStatus("ACTIVE");
                when(repository.findByOnboardingCaseId("case123"))
                                .thenReturn(Optional.of(existingSession));

                // When
                TaxVerificationView result = service.getTaxVerificationStatus("case123");

                // Then
                assertNotNull(result);
                assertEquals("case123", result.getOnboardingCaseId());
                assertEquals("ACTIVE", result.getProviderStatus());
        }

        @Test
        void getTaxVerificationStatus_WhenSessionDoesNotExist_ThrowsNotFoundException() {
                // Given
                when(repository.findByOnboardingCaseId("case123"))
                                .thenReturn(Optional.empty());

                // When & Then
                assertThrows(NotFoundException.class, () -> {
                        service.getTaxVerificationStatus("case123");
                });
        }

        @Test
        void submitTaxDocuments_MasksTaxIdInResponse() {
                // Given
                when(repository.findByOnboardingCaseId("case123"))
                                .thenReturn(Optional.empty());
                when(repository.save(any(TaxVerificationSession.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                TaxValidationResponse validationResponse = new TaxValidationResponse();
                validationResponse.setValid(true);
                validationResponse.setStatus("ACTIVE");

                when(taxClient.validateTaxId(any(), any(), any(), any(), any()))
                                .thenReturn(validationResponse);

                // When
                TaxVerificationView result = service.submitTaxDocuments("case123", validRequest);

                // Then
                assertNotNull(result.getMaskedTaxId());
                assertTrue(result.getMaskedTaxId().contains("***"));
                assertFalse(result.getMaskedTaxId().contains("27AAPFU0939F1ZV"));
        }
}
