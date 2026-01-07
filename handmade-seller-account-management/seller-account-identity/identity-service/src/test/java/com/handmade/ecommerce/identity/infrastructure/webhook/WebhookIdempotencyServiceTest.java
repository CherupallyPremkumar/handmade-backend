package com.handmade.ecommerce.identity.infrastructure.webhook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for WebhookIdempotencyService
 */
@ExtendWith(MockitoExtension.class)
class WebhookIdempotencyServiceTest {

    @Mock
    private WebhookIdempotencyRepository repository;

    @InjectMocks
    private WebhookIdempotencyService service;

    @Test
    void registerIfNew_WhenEventIsNew_ReturnsTrue() {
        // Given
        when(repository.save(any(WebhookIdempotencyRecord.class))).thenReturn(new WebhookIdempotencyRecord());

        // When
        boolean result = service.registerIfNew("evt_123", "identity_verification.verified");

        // Then
        assertTrue(result);
        verify(repository, times(1)).save(any(WebhookIdempotencyRecord.class));
    }

    @Test
    void registerIfNew_WhenEventAlreadyProcessed_ReturnsFalse() {
        // Given
        when(repository.save(any(WebhookIdempotencyRecord.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate key"));

        // When
        boolean result = service.registerIfNew("evt_123", "identity_verification.verified");

        // Then
        assertFalse(result);
        verify(repository, times(1)).save(any(WebhookIdempotencyRecord.class));
    }

    @Test
    void isProcessed_WhenExists_ReturnsTrue() {
        // Given
        when(repository.existsByEventId("evt_123")).thenReturn(true);

        // When
        boolean result = service.isProcessed("evt_123");

        // Then
        assertTrue(result);
    }

    @Test
    void isProcessed_WhenDoesNotExist_ReturnsFalse() {
        // Given
        when(repository.existsByEventId("evt_123")).thenReturn(false);

        // When
        boolean result = service.isProcessed("evt_123");

        // Then
        assertFalse(result);
    }

    @Test
    void cleanup_DeletesRecords_ReturnsCount() {
        // Given
        LocalDateTime olderThan = LocalDateTime.now().minusDays(1);
        when(repository.count()).thenReturn(10L, 5L);

        // When
        long deleted = service.cleanup(olderThan);

        // Then
        assertEquals(5L, deleted);
        verify(repository, times(1)).deleteByProcessedAtBefore(olderThan);
    }
}
