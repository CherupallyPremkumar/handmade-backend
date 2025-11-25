package com.handmade.ecommerce.shipping.core;

import com.handmade.ecommerce.shipping.model.Shipment;
import com.handmade.ecommerce.shipping.model.ShippingAddress;
import com.handmade.ecommerce.shipping.model.TrackingInfo;
import java.math.BigDecimal;

/**
 * CarrierPlugin - Interface that all carrier plugins must implement
 * This enables true plugin architecture - each carrier is a separate module
 */
public interface CarrierPlugin {
    
    /**
     * Get unique carrier code (e.g., "FEDEX", "UPS", "DHL")
     * Used for plugin discovery and selection
     */
    String getCarrierCode();
    
    /**
     * Get human-readable carrier name
     */
    String getCarrierName();
    
    /**
     * Get carrier description
     */
    String getDescription();
    
    /**
     * Check if this carrier is enabled
     * Can be controlled via configuration
     */
    boolean isEnabled();
    
    /**
     * Create shipping label and return tracking number
     * 
     * @param shipment Shipment details
     * @param address Shipping address
     * @return Tracking number
     */
    String createShippingLabel(Shipment shipment, ShippingAddress address);
    
    /**
     * Get tracking information for a shipment
     * 
     * @param trackingNumber Tracking number
     * @return Tracking information
     */
    TrackingInfo getTrackingInfo(String trackingNumber);
    
    /**
     * Calculate shipping cost
     * 
     * @param from Origin address
     * @param to Destination address
     * @param weight Package weight in kg
     * @return Shipping cost
     */
    BigDecimal calculateShippingCost(ShippingAddress from, ShippingAddress to, double weight);
    
    /**
     * Validate if carrier can ship to this address
     * 
     * @param address Shipping address
     * @return true if carrier supports this address
     */
    boolean supportsAddress(ShippingAddress address);
    
    /**
     * Get estimated delivery days
     * 
     * @param from Origin address
     * @param to Destination address
     * @return Estimated delivery days
     */
    int getEstimatedDeliveryDays(ShippingAddress from, ShippingAddress to);
    
    /**
     * Cancel shipment
     * 
     * @param trackingNumber Tracking number
     * @return true if cancelled successfully
     */
    boolean cancelShipment(String trackingNumber);
}
