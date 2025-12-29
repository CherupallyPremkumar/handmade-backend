package com.handmade.ecommerce.pricing.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.handmade.ecommerce.pricing.command.CreatePriceRuleCommand;

/**
 * CreatePriceCommand - Amazon-style pricing
 */
public class CreatePriceCommand {
    private String id;
    private String variantId;

    // Amazon-style pricing
    private BigDecimal listPrice; // MSRP / Original price
    private BigDecimal currentPrice; // Actual selling price
    private String currency;

    // Auto-calculated discount (optional - will be calculated if not provided)
    private BigDecimal discountAmount;
    private Integer discountPercent;

    // Special pricing
    private BigDecimal primePrice; // Prime member price
    private BigDecimal subscribeSavePrice; // Subscribe & Save price
    private Integer subscribeSavePercent = 15; // Default 15%

    // Effective dates
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;

    // Scalable: Regional pricing support
    private List<CreateRegionalPriceCommand> regionalPrices;

    // Scalable: Dynamic pricing rules
    private List<CreatePriceRuleCommand> priceRules;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getPrimePrice() {
        return primePrice;
    }

    public void setPrimePrice(BigDecimal primePrice) {
        this.primePrice = primePrice;
    }

    public BigDecimal getSubscribeSavePrice() {
        return subscribeSavePrice;
    }

    public void setSubscribeSavePrice(BigDecimal subscribeSavePrice) {
        this.subscribeSavePrice = subscribeSavePrice;
    }

    public Integer getSubscribeSavePercent() {
        return subscribeSavePercent;
    }

    public void setSubscribeSavePercent(Integer subscribeSavePercent) {
        this.subscribeSavePercent = subscribeSavePercent;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDateTime getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDateTime effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    /**
     * Backward compatibility - returns currentPrice
     * 
     * @deprecated Use getCurrentPrice() instead
     */
    @Deprecated
    public Double getAmount() {
        return currentPrice != null ? currentPrice.doubleValue() : null;
    }

    /**
     * Backward compatibility
     * 
     * @deprecated Use setCurrentPrice() instead
     */
    @Deprecated
    public void setAmount(Double amount) {
        this.currentPrice = amount != null ? BigDecimal.valueOf(amount) : null;
    }

    /**
     * Backward compatibility - returns currentPrice after discount
     * 
     * @deprecated Use getCurrentPrice() instead
     */
    @Deprecated
    public Double getFinalPayable() {
        return currentPrice != null ? currentPrice.doubleValue() : null;
    }

    /**
     * Backward compatibility
     * 
     * @deprecated Use setCurrentPrice() instead
     */
    @Deprecated
    public void setFinalPayable(Double finalPayable) {
        this.currentPrice = finalPayable != null ? BigDecimal.valueOf(finalPayable) : null;
    }

    /**
     * Get regional prices
     */
    public List<CreateRegionalPriceCommand> getRegionalPrices() {
        return regionalPrices;
    }

    /**
     * Set regional prices
     */
    public void setRegionalPrices(List<CreateRegionalPriceCommand> regionalPrices) {
        this.regionalPrices = regionalPrices;
    }

    /**
     * Get price rules
     */
    public List<CreatePriceRuleCommand> getPriceRules() {
        return priceRules;
    }

    /**
     * Set price rules
     */
    public void setPriceRules(List<CreatePriceRuleCommand> priceRules) {
        this.priceRules = priceRules;
    }
}
