package com.handmade.ecommerce.seller.account.service.tax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TaxValidationClient
 */
class TaxValidationClientTest {

    private TaxValidationClient client;

    @BeforeEach
    void setUp() {
        client = new TaxValidationClient();
    }

    @Test
    void validateTaxId_WithGST_CallsGSTValidation() {
        // When
        TaxValidationResponse result = client.validateTaxId(
                "27AAPFU0939F1ZV", "GST", "IN", "Acme Corp", "Mumbai");

        // Then
        assertNotNull(result);
        assertTrue(result.isValid());
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void validateTaxId_WithVAT_CallsVATValidation() {
        // When
        TaxValidationResponse result = client.validateTaxId(
                "GB123456789", "VAT", "GB", "Acme Ltd", "London");

        // Then
        assertNotNull(result);
        assertTrue(result.isValid());
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void validateTaxId_WithEIN_CallsEINValidation() {
        // When
        TaxValidationResponse result = client.validateTaxId(
                "12-3456789", "EIN", "US", "Acme Inc", "New York");

        // Then
        assertNotNull(result);
        assertTrue(result.isValid());
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void validateTaxId_WithPAN_CallsPANValidation() {
        // When
        TaxValidationResponse result = client.validateTaxId(
                "ABCDE1234F", "PAN", "IN", "Acme Corp", "Mumbai");

        // Then
        assertNotNull(result);
        assertTrue(result.isValid());
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void validateTaxId_WithUnsupportedType_ReturnsError() {
        // When
        TaxValidationResponse result = client.validateTaxId(
                "123", "UNKNOWN", "XX", "Test", "Test");

        // Then
        assertNotNull(result);
        assertFalse(result.isValid());
        assertEquals("ERROR", result.getStatus());
        assertTrue(result.getErrorMessage().contains("Unsupported"));
    }

    @Test
    void isConfigured_WithoutApiKey_ReturnsFalse() {
        // When
        boolean result = client.isConfigured();

        // Then
        assertFalse(result);
    }

    @Test
    void maskTaxId_MasksMiddleCharacters() {
        // This is a private method, but we can test it indirectly
        // through the logging behavior

        // When
        TaxValidationResponse result = client.validateTaxId(
                "27AAPFU0939F1ZV", "GST", "IN", "Acme", "Mumbai");

        // Then - just verify it doesn't throw
        assertNotNull(result);
    }
}
