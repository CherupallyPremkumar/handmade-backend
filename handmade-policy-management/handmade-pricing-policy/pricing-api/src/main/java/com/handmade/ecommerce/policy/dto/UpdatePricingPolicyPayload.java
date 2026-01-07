package com.handmade.ecommerce.policy.dto;

public class UpdatePricingPolicyPayload {
    private Long minPriceCents;
    private Long maxPriceCents;
    private Integer maxMarkupPercentage;
    private Integer maxPriceChangesPerDay;
    
    public Long getMinPriceCents() { return minPriceCents; }
    public void setMinPriceCents(Long minPriceCents) { this.minPriceCents = minPriceCents; }
    
    public Long getMaxPriceCents() { return maxPriceCents; }
    public void setMaxPriceCents(Long maxPriceCents) { this.maxPriceCents = maxPriceCents; }
    
    public Integer getMaxMarkupPercentage() { return maxMarkupPercentage; }
    public void setMaxMarkupPercentage(Integer maxMarkupPercentage) { this.maxMarkupPercentage = maxMarkupPercentage; }
    
    public Integer getMaxPriceChangesPerDay() { return maxPriceChangesPerDay; }
    public void setMaxPriceChangesPerDay(Integer maxPriceChangesPerDay) { this.maxPriceChangesPerDay = maxPriceChangesPerDay; }
}
