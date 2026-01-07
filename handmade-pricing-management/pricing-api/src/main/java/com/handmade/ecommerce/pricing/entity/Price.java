package com.handmade.ecommerce.pricing.entity;

import jakarta.persistence.Transient;
import org.chenile.workflow.model.TransientMap;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.handmade.ecommerce.pricing.enums.PriceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Price Entity (Aggregate Root)
 * Amazon-style pricing with multiple price types, regional pricing, and history
 */
@Entity
@Table(name = "hm_price")
@Getter
@Setter
public class Price extends AbstractJpaStateEntity {

    @Transient
    public org.chenile.workflow.model.TransientMap transientMap = new org.chenile.workflow.model.TransientMap();

    @Column(name = "variant_id", nullable = false)
    private String variantId;

    // Base pricing (Amazon-style)
    @Column(name = "list_price", precision = 10, scale = 2)
    private BigDecimal listPrice; // MSRP / Original price

    @Column(name = "current_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentPrice; // Actual selling price

    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency = "USD";

    // Discount information (auto-calculated)
    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    // Special pricing tiers
    @Column(name = "prime_price", precision = 10, scale = 2)
    private BigDecimal primePrice; // Price for Prime members

    @Column(name = "subscribe_save_price", precision = 10, scale = 2)
    private BigDecimal subscribeSavePrice; // Subscription discount price

    @Column(name = "subscribe_save_percent")
    private Integer subscribeSavePercent = 15; // Default 15% off

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type", nullable = false, length = 50)
    private PriceType priceType = PriceType.STANDARD;

    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Regional price overrides
    @OneToMany(mappedBy = "price", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RegionalPrice> regionalPrices = new ArrayList<>();

    // Pricing rules (competitive pricing, dynamic pricing)
    @OneToMany(mappedBy = "price", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PriceRule> priceRules = new ArrayList<>();

    // Complete price change history
    @OneToMany(mappedBy = "price", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PriceHistory> priceHistory = new ArrayList<>();

    /**
     * Add regional price override
     */
    public void addRegionalPrice(RegionalPrice regionalPrice) {
        regionalPrice.setPrice(this);
        regionalPrices.add(regionalPrice);
    }

    /**
     * Add price rule
     */
    public void addPriceRule(PriceRule rule) {
        rule.setPrice(this);
        priceRules.add(rule);
    }

    /**
     * Update current price and record history
     */
    public void updateCurrentPrice(BigDecimal newPrice, String changedBy, String reason) {
        PriceHistory history = new PriceHistory();
        history.setPrice(this);
        history.setOldPrice(this.currentPrice);
        history.setNewPrice(newPrice);
        history.setCurrency(this.baseCurrency);
        history.setChangedBy(changedBy);
        history.setChangeReason(reason);
        history.setChangedAt(LocalDateTime.now());

        priceHistory.add(history);
        this.currentPrice = newPrice;

        // Recalculate discount
        calculateDiscount();
    }

    /**
     * Calculate discount amount and percentage
     */
    public void calculateDiscount() {
        if (listPrice != null && currentPrice != null) {
            this.discountAmount = listPrice.subtract(currentPrice);

            if (listPrice.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal percent = discountAmount
                        .multiply(BigDecimal.valueOf(100))
                        .divide(listPrice, 2, java.math.RoundingMode.HALF_UP);
                this.discountPercent = percent.intValue();
            }
        }
    }

    /**
     * Calculate Subscribe & Save price
     */
    public void calculateSubscribeSavePrice() {
        if (currentPrice != null && subscribeSavePercent != null) {
            BigDecimal discount = currentPrice
                    .multiply(BigDecimal.valueOf(subscribeSavePercent))
                    .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
            this.subscribeSavePrice = currentPrice.subtract(discount);
        }
    }

    /**
     * Check if price is currently effective
     */
    public boolean isEffective() {
        LocalDateTime now = LocalDateTime.now();
        boolean afterStart = effectiveFrom == null || now.isAfter(effectiveFrom);
        boolean beforeEnd = effectiveTo == null || now.isBefore(effectiveTo);
        return isActive && afterStart && beforeEnd;
    }

    /**
     * Get effective price for a customer (considers Prime, region, etc.)
     */
    public BigDecimal getEffectivePrice(boolean isPrimeMember, String region) {
        // Priority: Regional > Prime > Current
        if (region != null) {
            for (RegionalPrice rp : regionalPrices) {
                if (region.equals(rp.getRegion()) && rp.getIsActive()) {
                    return rp.getPriceAmount();
                }
            }
        }

        if (isPrimeMember && primePrice != null) {
            return primePrice;
        }

        return currentPrice;
    }
}
