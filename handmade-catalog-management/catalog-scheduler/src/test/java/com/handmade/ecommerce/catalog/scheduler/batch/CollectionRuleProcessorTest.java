package com.handmade.ecommerce.catalog.scheduler.batch;

import com.handmade.ecommerce.catalog.model.Collection;
import com.handmade.ecommerce.catalog.model.CollectionType;
import com.handmade.ecommerce.catalog.service.integration.ExternalProductDto;
import com.handmade.ecommerce.catalog.service.rule.DynamicRuleEvaluator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CollectionRuleProcessor
 * Tests the Spring Batch ItemProcessor that evaluates products against collection rules
 */
@ExtendWith(MockitoExtension.class)
class CollectionRuleProcessorTest {

    @Mock
    private DynamicRuleEvaluator ruleEvaluator;

    @InjectMocks
    private CollectionRuleProcessor processor;

    private Collection collection;
    private ExternalProductDto product;

    @BeforeEach
    void setUp() {
        collection = new Collection();
        collection.setId("col-1");
        collection.setName("Budget Items");
        collection.setType(CollectionType.DYNAMIC);
        collection.setRuleExpression("price < 50.00");

        product = new ExternalProductDto();
        product.setId("prod-1");
        product.setName("Test Product");
        product.setPrice(new BigDecimal("35.00"));
        product.setActive(true);
    }

    @Test
    @DisplayName("Should process product that matches collection rule")
    void process_ProductMatchesRule_ReturnsMapping() throws Exception {
        // Arrange
        when(ruleEvaluator.matches(collection.getRuleExpression(), product)).thenReturn(true);

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCollectionId()).isEqualTo("col-1");
        assertThat(result.getProductId()).isEqualTo("prod-1");
        
        verify(ruleEvaluator).matches(collection.getRuleExpression(), product);
    }

    @Test
    @DisplayName("Should return null when product does not match rule")
    void process_ProductDoesNotMatch_ReturnsNull() throws Exception {
        // Arrange
        when(ruleEvaluator.matches(collection.getRuleExpression(), product)).thenReturn(false);

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNull();
        
        verify(ruleEvaluator).matches(collection.getRuleExpression(), product);
    }

    @Test
    @DisplayName("Should handle complex AND rule")
    void process_ComplexAndRule_EvaluatesCorrectly() throws Exception {
        // Arrange
        collection.setRuleExpression("price < 50.00 && active == true");
        when(ruleEvaluator.matches(collection.getRuleExpression(), product)).thenReturn(true);

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNotNull();
        verify(ruleEvaluator).matches(collection.getRuleExpression(), product);
    }

    @Test
    @DisplayName("Should handle complex OR rule")
    void process_ComplexOrRule_EvaluatesCorrectly() throws Exception {
        // Arrange
        collection.setRuleExpression("price < 20.00 || featured == true");
        when(ruleEvaluator.matches(collection.getRuleExpression(), product)).thenReturn(true);

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should handle null rule expression gracefully")
    void process_NullRuleExpression_ReturnsNull() throws Exception {
        // Arrange
        collection.setRuleExpression(null);
        when(ruleEvaluator.matches(null, product)).thenReturn(false);

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should handle inactive product")
    void process_InactiveProduct_ReturnsNullOrProcesses() throws Exception {
        // Arrange
        product.setActive(false);
        collection.setRuleExpression("active == false");
        when(ruleEvaluator.matches(collection.getRuleExpression(), product)).thenReturn(true);

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should handle price boundary conditions")
    void process_PriceBoundary_EvaluatesCorrectly() throws Exception {
        // Arrange
        product.setPrice(new BigDecimal("50.00")); // Exactly at boundary
        collection.setRuleExpression("price <= 50.00");
        when(ruleEvaluator.matches(collection.getRuleExpression(), product)).thenReturn(true);

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should handle rule evaluation exception")
    void process_RuleEvaluationThrowsException_ReturnsNull() throws Exception {
        // Arrange
        when(ruleEvaluator.matches(anyString(), any())).thenThrow(new RuntimeException("Invalid rule"));

        // Act
        var result = processor.process(product);

        // Assert
        assertThat(result).isNull(); // Should handle exception gracefully
    }
}
