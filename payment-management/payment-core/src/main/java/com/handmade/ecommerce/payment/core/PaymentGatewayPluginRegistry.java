package com.handmade.ecommerce.payment.core;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PaymentGatewayPluginRegistry - Discovers and manages all payment gateway plugins
 */
@Service
public class PaymentGatewayPluginRegistry {
    
    private final Map<String, PaymentGatewayPlugin> plugins;
    
    /**
     * Constructor - Spring auto-injects all PaymentGatewayPlugin implementations
     */
    public PaymentGatewayPluginRegistry(List<PaymentGatewayPlugin> gatewayPlugins) {
        this.plugins = new HashMap<>();
        
        for (PaymentGatewayPlugin plugin : gatewayPlugins) {
            if (plugin.isEnabled()) {
                plugins.put(plugin.getGatewayCode(), plugin);
                System.out.println(String.format(
                    "✅ Registered payment gateway plugin: %s (%s)",
                    plugin.getGatewayCode(),
                    plugin.getGatewayName()
                ));
            } else {
                System.out.println(String.format(
                    "⏸️  Skipped disabled payment gateway: %s",
                    plugin.getGatewayCode()
                ));
            }
        }
        
        System.out.println("💳 Total active payment gateways: " + plugins.size());
    }
    
    /**
     * Get gateway plugin by code
     */
    public PaymentGatewayPlugin getGateway(String gatewayCode) {
        PaymentGatewayPlugin plugin = plugins.get(gatewayCode);
        if (plugin == null) {
            throw new GatewayNotFoundException(
                "Payment gateway not found: " + gatewayCode + 
                ". Available gateways: " + getAvailableGateways()
            );
        }
        return plugin;
    }
    
    /**
     * Get all available gateway codes
     */
    public List<String> getAvailableGateways() {
        return new ArrayList<>(plugins.keySet());
    }
    
    /**
     * Get all gateway plugins
     */
    public Collection<PaymentGatewayPlugin> getAllGateways() {
        return plugins.values();
    }
    
    /**
     * Get gateway details
     */
    public Map<String, String> getGatewayDetails() {
        return plugins.values().stream()
            .collect(Collectors.toMap(
                PaymentGatewayPlugin::getGatewayCode,
                PaymentGatewayPlugin::getGatewayName
            ));
    }
    
    /**
     * Check if gateway is available
     */
    public boolean isGatewayAvailable(String gatewayCode) {
        return plugins.containsKey(gatewayCode);
    }
    
    /**
     * Get gateways that support a currency
     */
    public List<PaymentGatewayPlugin> getGatewaysForCurrency(String currency) {
        return plugins.values().stream()
            .filter(gateway -> Arrays.asList(gateway.getSupportedCurrencies()).contains(currency))
            .collect(Collectors.toList());
    }
    
    /**
     * Exception for gateway not found
     */
    public static class GatewayNotFoundException extends RuntimeException {
        public GatewayNotFoundException(String message) {
            super(message);
        }
    }
}
