package com.handmade.ecommerce.compliance.domain.valueobject;

/**
 * Compliance requirement severity levels
 */
public enum ComplianceSeverity {
    CRITICAL("Must be resolved immediately"),
    HIGH("Must be resolved within 7 days"),
    MEDIUM("Must be resolved within 30 days"),
    LOW("Advisory only");
    
    private final String description;
    
    ComplianceSeverity(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
