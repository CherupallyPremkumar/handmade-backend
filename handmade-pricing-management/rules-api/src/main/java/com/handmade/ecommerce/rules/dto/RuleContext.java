package com.handmade.ecommerce.rules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Rule Context
 * Contains all information needed to evaluate rules
 * This is engine-agnostic - works with Easy Rules and Drools
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleContext {

    // Product info
    private String productId;
    private String variantId;
    private String categoryId;
    private String sellerId;

    // Customer info
    private String customerId;
    private String customerSegment;
    private boolean firstOrder;

    // Quantity and pricing
    private Integer quantity;
    private String regionCode;
    private String stateCode;
    private String currency;

    // Time context
    private LocalDateTime timestamp;

    /**
     * Default quantity to 1 if not set
     */
    public Integer getQuantity() {
        return quantity != null ? quantity : 1;
    }

    /**
     * Default timestamp to now if not set
     */
    public LocalDateTime getTimestamp() {
        return timestamp != null ? timestamp : LocalDateTime.now();
    }
}
