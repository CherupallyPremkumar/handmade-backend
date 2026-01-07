package com.handmade.ecommerce.policy.event;

import com.handmade.ecommerce.policy.domain.valueobject.PricingViolationType;
import java.util.List;

public class PriceViolationDetectedEvent {
    private String priceId;
    private String productId;
    private String sellerId;
    private Long priceCents;
    private List<PricingViolationType> violations;
    private String policyVersion;
    
    public String getPriceId() { return priceId; }
    public void setPriceId(String priceId) { this.priceId = priceId; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public Long getPriceCents() { return priceCents; }
    public void setPriceCents(Long priceCents) { this.priceCents = priceCents; }
    
    public List<PricingViolationType> getViolations() { return violations; }
    public void setViolations(List<PricingViolationType> violations) { this.violations = violations; }
    
    public String getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(String policyVersion) { this.policyVersion = policyVersion; }
}
