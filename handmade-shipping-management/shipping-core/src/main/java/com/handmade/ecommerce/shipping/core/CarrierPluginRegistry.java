package com.handmade.ecommerce.shipping.core;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CarrierPluginRegistry - Discovers and manages all carrier plugins
 * Spring auto-discovers all CarrierPlugin implementations
 */
@Service
public class CarrierPluginRegistry {
    
    private final Map<String, CarrierPlugin> plugins;
    
    /**
     * Constructor - Spring auto-injects all CarrierPlugin implementations
     */
    public CarrierPluginRegistry(List<CarrierPlugin> carrierPlugins) {
        this.plugins = new HashMap<>();
        
        for (CarrierPlugin plugin : carrierPlugins) {
            if (plugin.isEnabled()) {
                plugins.put(plugin.getCarrierCode(), plugin);
                System.out.println(String.format(
                    "✅ Registered carrier plugin: %s (%s)",
                    plugin.getCarrierCode(),
                    plugin.getCarrierName()
                ));
            } else {
                System.out.println(String.format(
                    "⏸️  Skipped disabled carrier plugin: %s",
                    plugin.getCarrierCode()
                ));
            }
        }
        
        System.out.println("📦 Total active carriers: " + plugins.size());
    }
    
    /**
     * Get carrier plugin by code
     */
    public CarrierPlugin getCarrier(String carrierCode) {
        CarrierPlugin plugin = plugins.get(carrierCode);
        if (plugin == null) {
            throw new CarrierNotFoundException(
                "Carrier not found: " + carrierCode + 
                ". Available carriers: " + getAvailableCarriers()
            );
        }
        return plugin;
    }
    
    /**
     * Get all available carrier codes
     */
    public List<String> getAvailableCarriers() {
        return new ArrayList<>(plugins.keySet());
    }
    
    /**
     * Get all carrier plugins
     */
    public Collection<CarrierPlugin> getAllCarriers() {
        return plugins.values();
    }
    
    /**
     * Get carrier details (code -> name mapping)
     */
    public Map<String, String> getCarrierDetails() {
        return plugins.values().stream()
            .collect(Collectors.toMap(
                CarrierPlugin::getCarrierCode,
                CarrierPlugin::getCarrierName
            ));
    }
    
    /**
     * Check if carrier is available
     */
    public boolean isCarrierAvailable(String carrierCode) {
        return plugins.containsKey(carrierCode);
    }
    
    /**
     * Get carriers that support a specific address
     */
    public List<CarrierPlugin> getCarriersForAddress(com.handmade.ecommerce.shipping.model.ShippingAddress address) {
        return plugins.values().stream()
            .filter(carrier -> carrier.supportsAddress(address))
            .collect(Collectors.toList());
    }
    
    /**
     * Exception for carrier not found
     */
    public static class CarrierNotFoundException extends RuntimeException {
        public CarrierNotFoundException(String message) {
            super(message);
        }
    }
}
