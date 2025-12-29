package com.handmade.ecommerce.shipping.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * TrackingInfo - Value Object
 * Tracking information for a shipment
 */
@Embeddable
public class TrackingInfo {
    
    @Column(name = "tracking_url", length = 500)
    private String trackingUrl;
    
    @Column(name = "current_location", length = 200)
    private String currentLocation;
    
    @Column(name = "last_update_message", length = 500)
    private String lastUpdateMessage;
    
    // Getters and Setters
    
    public String getTrackingUrl() {
        return trackingUrl;
    }
    
    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }
    
    public String getCurrentLocation() {
        return currentLocation;
    }
    
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public String getLastUpdateMessage() {
        return lastUpdateMessage;
    }
    
    public void setLastUpdateMessage(String lastUpdateMessage) {
        this.lastUpdateMessage = lastUpdateMessage;
    }
}
