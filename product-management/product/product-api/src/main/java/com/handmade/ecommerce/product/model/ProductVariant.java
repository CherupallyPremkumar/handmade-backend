package com.handmade.ecommerce.product.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Product Variant Entity
 * Represents a specific variation of a product (e.g., size, color)
 * Part of Product aggregate
 */
@Entity
@Table(name = "hm_product_variant")
@Getter
@Setter
public class ProductVariant extends AbstractJpaStateEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

    @Column(name = "sku", nullable = false, unique = true, length = 100)
    private String sku;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "compare_at_price", precision = 10, scale = 2)
    private BigDecimal compareAtPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "variant_status", nullable = false, length = 50)
    private VariantStatus variantStatus = VariantStatus.DRAFT;

    /**
     * JSON field storing variant attributes (color, size, etc.)
     * Example: {"color": "Blue", "size": "Large"}
     */
    @Column(name = "attributes", columnDefinition = "JSON")
    private String attributes;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    /**
     * Update variant price
     * May trigger state transition if price change is significant
     */
    public void updatePrice(BigDecimal newPrice) {
        if (this.variantStatus == VariantStatus.ACTIVE) {
            // Check if price change is significant (>20%)
            if (isPriceChangeSignificant(newPrice)) {
                this.variantStatus = VariantStatus.PENDING_PRICE_CHANGE;
            }
        }
        this.price = newPrice;
    }

    /**
     * Activate variant
     */
    public void activate() {
        if (this.variantStatus == VariantStatus.PENDING_APPROVAL) {
            this.variantStatus = VariantStatus.ACTIVE;
        }
    }

    /**
     * Deactivate variant
     */
    public void deactivate() {
        if (this.variantStatus == VariantStatus.ACTIVE) {
            this.variantStatus = VariantStatus.INACTIVE;
        }
    }

    /**
     * Mark variant as out of stock
     */
    public void markOutOfStock() {
        if (this.variantStatus == VariantStatus.ACTIVE) {
            this.variantStatus = VariantStatus.OUT_OF_STOCK;
        }
    }

    private boolean isPriceChangeSignificant(BigDecimal newPrice) {
        if (this.price == null || newPrice == null) {
            return false;
        }
        BigDecimal change = newPrice.subtract(this.price).abs();
        BigDecimal threshold = this.price.multiply(new BigDecimal("0.20")); // 20%
        return change.compareTo(threshold) > 0;
    }
}
