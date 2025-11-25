package com.handmade.ecommerce.shipping.service.carrier;

import com.handmade.ecommerce.shipping.model.Shipment;
import com.handmade.ecommerce.shipping.model.ShippingAddress;
import com.handmade.ecommerce.shipping.model.TrackingInfo;
import java.math.BigDecimal;

/**
 * AbstractCarrierService - Base class for all carrier implementations
 * Uses Template Method pattern
 */
public abstract class AbstractCarrierService {
    
    /**
     * Get carrier code (e.g., "FEDEX", "UPS")
     */
    public abstract String getCarrierCode();
    
    /**
     * Get carrier display name
     */
    public abstract String getCarrierName();
    
    /**
     * Create shipping label - implemented by each carrier
     */
    protected abstract String doCreateLabel(Shipment shipment, ShippingAddress address);
    
    /**
     * Get tracking info - implemented by each carrier
     */
    protected abstract TrackingInfo doGetTrackingInfo(String trackingNumber);
    
    /**
     * Calculate shipping cost - implemented by each carrier
     */
    protected abstract BigDecimal doCalculateShippingCost(
        ShippingAddress from, 
        ShippingAddress to, 
        double weight
    );
    
    /**
     * Validate address - can be overridden
     */
    protected boolean validateAddress(ShippingAddress address) {
        return address != null 
            && address.getAddressLine1() != null 
            && address.getCity() != null
            && address.getPostalCode() != null
            && address.getCountry() != null;
    }
    
    /**
     * Template method - creates label with validation
     */
    public final String createShippingLabel(Shipment shipment, ShippingAddress address) {
        // Pre-processing (common logic)
        if (!validateAddress(address)) {
            throw new IllegalArgumentException("Invalid shipping address");
        }
        
        // Call carrier-specific implementation
        String trackingNumber = doCreateLabel(shipment, address);
        
        // Post-processing (common logic)
        logLabelCreation(shipment.getShipmentId(), trackingNumber);
        
        return trackingNumber;
    }
    
    /**
     * Template method - gets tracking info with caching
     */
    public final TrackingInfo getTrackingInfo(String trackingNumber) {
        // Pre-processing
        if (trackingNumber == null || trackingNumber.isEmpty()) {
            throw new IllegalArgumentException("Tracking number required");
        }
        
        // Check cache first (optional)
        // TrackingInfo cached = cache.get(trackingNumber);
        // if (cached != null) return cached;
        
        // Call carrier-specific implementation
        TrackingInfo info = doGetTrackingInfo(trackingNumber);
        
        // Post-processing
        // cache.put(trackingNumber, info);
        
        return info;
    }
    
    /**
     * Template method - calculates shipping cost
     */
    public final BigDecimal calculateShippingCost(
        ShippingAddress from, 
        ShippingAddress to, 
        double weight
    ) {
        // Validation
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        
        // Call carrier-specific implementation
        BigDecimal cost = doCalculateShippingCost(from, to, weight);
        
        // Apply any markup/discount (common logic)
        return applyPlatformMarkup(cost);
    }
    
    /**
     * Common helper - apply platform markup
     */
    protected BigDecimal applyPlatformMarkup(BigDecimal cost) {
        // Add 5% platform fee
        return cost.multiply(new BigDecimal("1.05"));
    }
    
    /**
     * Common helper - log label creation
     */
    protected void logLabelCreation(String shipmentId, String trackingNumber) {
        System.out.println(String.format(
            "[%s] Label created for shipment %s: %s",
            getCarrierCode(),
            shipmentId,
            trackingNumber
        ));
    }
    
    /**
     * Hook method - can be overridden for carrier-specific logic
     */
    protected void onLabelCreated(String trackingNumber) {
        // Default: do nothing
        // Subclasses can override for custom behavior
    }
}
