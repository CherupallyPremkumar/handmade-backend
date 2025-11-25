package com.handmade.ecommerce.shipping.service.carrier;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CarrierServiceFactory - Factory to pick the right carrier
 * Uses inheritance hierarchy
 */
@Service
public class CarrierServiceFactory {
    
    private final Map<String, AbstractCarrierService> carriers;
    
    /**
     * Constructor - Spring auto-injects all AbstractCarrierService subclasses
     */
    public CarrierServiceFactory(List<AbstractCarrierService> carrierServices) {
        this.carriers = new HashMap<>();
        for (AbstractCarrierService carrier : carrierServices) {
            carriers.put(carrier.getCarrierCode(), carrier);
        }
        
        System.out.println("Registered carriers: " + carriers.keySet());
    }
    
    /**
     * Get carrier by code
     */
    public AbstractCarrierService getCarrier(String carrierCode) {
        AbstractCarrierService carrier = carriers.get(carrierCode);
        if (carrier == null) {
            throw new UnsupportedCarrierException(
                "Carrier not supported: " + carrierCode + 
                ". Available: " + carriers.keySet()
            );
        }
        return carrier;
    }
    
    /**
     * Get all supported carrier codes
     */
    public List<String> getSupportedCarriers() {
        return carriers.values().stream()
            .map(AbstractCarrierService::getCarrierCode)
            .toList();
    }
    
    /**
     * Get all carrier details
     */
    public Map<String, String> getCarrierDetails() {
        Map<String, String> details = new HashMap<>();
        carriers.values().forEach(carrier -> 
            details.put(carrier.getCarrierCode(), carrier.getCarrierName())
        );
        return details;
    }
    
    /**
     * Check if carrier is supported
     */
    public boolean isSupported(String carrierCode) {
        return carriers.containsKey(carrierCode);
    }
    
    /**
     * Exception for unsupported carriers
     */
    public static class UnsupportedCarrierException extends RuntimeException {
        public UnsupportedCarrierException(String message) {
            super(message);
        }
    }
}
