package com.handmade.ecommerce.policy.dto;

public class PriceValidationRequest {
    private String country;
    private String category;
    private Long priceCents;
    private String productId;
    private String sellerId;
    private Long costCents;
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Long getPriceCents() { return priceCents; }
    public void setPriceCents(Long priceCents) { this.priceCents = priceCents; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public Long getCostCents() { return costCents; }
    public void setCostCents(Long costCents) { this.costCents = costCents; }
}
