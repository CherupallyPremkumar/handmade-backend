package com.handmade.ecommerce.payment.service.gateway;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PaymentGatewayFactory - Factory to pick the right payment gateway
 * Uses inheritance hierarchy
 */
@Service
public class PaymentGatewayFactory {

    private final Map<String, AbstractPaymentGateway> gateways;

    /**
     * Constructor - Spring auto-injects all AbstractPaymentGateway subclasses
     */
    public PaymentGatewayFactory(List<AbstractPaymentGateway> paymentGateways) {
        this.gateways = new HashMap<>();
        for (AbstractPaymentGateway gateway : paymentGateways) {
            gateways.put(gateway.getGatewayCode(), gateway);
        }

        System.out.println("Registered payment gateways: " + gateways.keySet());
    }

    /**
     * Get gateway by code
     */
    public AbstractPaymentGateway getGateway(String gatewayCode) {
        AbstractPaymentGateway gateway = gateways.get(gatewayCode);
        if (gateway == null) {
            throw new UnsupportedGatewayException(
                    "Payment gateway not supported: " + gatewayCode +
                            ". Available: " + gateways.keySet());
        }
        return gateway;
    }

    /**
     * Get all supported gateway codes
     */
    public List<String> getSupportedGateways() {
        return gateways.values().stream()
                .map(AbstractPaymentGateway::getGatewayCode)
                .toList();
    }

    /**
     * Get all gateway details
     */
    public Map<String, String> getGatewayDetails() {
        Map<String, String> details = new HashMap<>();
        gateways.values().forEach(gateway -> details.put(gateway.getGatewayCode(), gateway.getGatewayName()));
        return details;
    }

    /**
     * Check if gateway is supported
     */
    public boolean isSupported(String gatewayCode) {
        return gateways.containsKey(gatewayCode);
    }

    /**
     * Exception for unsupported gateways
     */
    public static class UnsupportedGatewayException extends RuntimeException {
        public UnsupportedGatewayException(String message) {
            super(message);
        }
    }
}
