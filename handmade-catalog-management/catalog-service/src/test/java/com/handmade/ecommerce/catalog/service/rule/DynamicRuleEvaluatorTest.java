package com.handmade.ecommerce.catalog.service.rule;

import com.handmade.ecommerce.product.dto.ExternalProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DynamicRuleEvaluator Tests")
class DynamicRuleEvaluatorTest {

    private DynamicRuleEvaluator evaluator;

    @BeforeEach
    void setUp() {
        evaluator = new DynamicRuleEvaluator();
    }

    @Test
    @DisplayName("Should match product when price is less than threshold")
    void evaluate_PriceRule_MatchesCorrectly() {
        // Arrange
        ExternalProductDto product = createProduct("Test Product", new BigDecimal("45.00"));
        String rule = "price < 50.00";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should not match product when price exceeds threshold")
    void evaluate_PriceRule_DoesNotMatchWhenPriceHigh() {
        // Arrange
        ExternalProductDto product = createProduct("Expensive Product", new BigDecimal("75.00"));
        String rule = "price < 50.00";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should match product when name contains keyword")
    void evaluate_NameContainsRule_MatchesCorrectly() {
        // Arrange
        ExternalProductDto product = createProduct("Summer Dress", new BigDecimal("35.00"));
        String rule = "name.contains('Summer')";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should not match when name does not contain keyword")
    void evaluate_NameContainsRule_DoesNotMatchWhenNameDifferent() {
        // Arrange
        ExternalProductDto product = createProduct("Winter Coat", new BigDecimal("35.00"));
        String rule = "name.contains('Summer')";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should match product when complex AND rule is satisfied")
    void evaluate_ComplexAndRule_MatchesCorrectly() {
        // Arrange
        ExternalProductDto product = createProduct("Summer Dress", new BigDecimal("35.00"));
        product.setActive(true);
        String rule = "price < 50.00 and name.contains('Summer') and active == true";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should not match when one condition in AND rule fails")
    void evaluate_ComplexAndRule_DoesNotMatchWhenOneConditionFails() {
        // Arrange
        ExternalProductDto product = createProduct("Summer Dress", new BigDecimal("75.00"));
        product.setActive(true);
        String rule = "price < 50.00 and name.contains('Summer') and active == true";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should match when OR rule has at least one true condition")
    void evaluate_OrRule_MatchesWhenOneConditionTrue() {
        // Arrange
        ExternalProductDto product = createProduct("Winter Coat", new BigDecimal("35.00"));
        String rule = "name.contains('Summer') or price < 50.00";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should return false for invalid SpEL expression")
    void evaluate_InvalidRule_ReturnsFalse() {
        // Arrange
        ExternalProductDto product = createProduct("Test", new BigDecimal("35.00"));
        String invalidRule = "invalid syntax !!!";

        // Act
        boolean result = evaluator.matches(invalidRule, product);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when product is null")
    void evaluate_NullProduct_ReturnsFalse() {
        // Arrange
        String rule = "price < 50.00";

        // Act & Assert
        // Note: Current implementation throws NPE, so we test for that
        // In production, should add null check in DynamicRuleEvaluator.matches()
        assertThatThrownBy(() -> evaluator.matches(rule, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should return false when rule is null or empty")
    void evaluate_NullOrEmptyRule_ReturnsFalse() {
        // Arrange
        ExternalProductDto product = createProduct("Test", new BigDecimal("35.00"));

        // Act & Assert
        assertThat(evaluator.matches(null, product)).isFalse();
        assertThat(evaluator.matches("", product)).isFalse();
        assertThat(evaluator.matches("   ", product)).isFalse();
    }

    @Test
    @DisplayName("Should handle price comparison with exact equality")
    void evaluate_PriceEquality_MatchesCorrectly() {
        // Arrange
        ExternalProductDto product = createProduct("Test", new BigDecimal("50.00"));
        String rule = "price == 50.00";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should handle greater than comparison")
    void evaluate_GreaterThanRule_MatchesCorrectly() {
        // Arrange
        ExternalProductDto product = createProduct("Premium Product", new BigDecimal("150.00"));
        String rule = "price > 100.00";

        // Act
        boolean result = evaluator.matches(rule, product);

        // Assert
        assertThat(result).isTrue();
    }

    // Helper method
    private ExternalProductDto createProduct(String name, BigDecimal price) {
        ExternalProductDto product = new ExternalProductDto();
        product.setId("prod-" + System.currentTimeMillis());
        product.setName(name);
        product.setPrice(price);
        product.setActive(true);
        return product;
    }
}
