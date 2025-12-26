package com.handmade.ecommerce.shipping.carrier.dhl;

import com.handmade.ecommerce.shipping.core.CarrierPlugin;
import com.handmade.ecommerce.shipping.model.Shipment;
import com.handmade.ecommerce.shipping.model.ShippingAddress;
import com.handmade.ecommerce.shipping.model.TrackingInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * DHLCarrierPlugin - DHL implementation as a plugin
 * This is a SEPARATE MODULE that can be deployed independently
 */
@Component
public class DHLCarrierPlugin implements CarrierPlugin {
    
    @Value("${carrier.dhl.enabled:true}")
    private boolean enabled;
    
    @Value("${carrier.dhl.api.key:}")
    private String apiKey;
    
    @Override
    public String getCarrierCode() {
        return "DHL";
    }
    
    @Override
    public String getCarrierName() {
        return "DHL Express";
    }
    
    @Override
    public String getDescription() {
        return "DHL Express - International shipping specialist";
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public String createShippingLabel(Shipment shipment, ShippingAddress address) {
        String trackingNumber = "DHL-" + System.currentTimeMillis();
        
        System.out.println(String.format(
            "📦 [DHL] Creating international label for shipment %s",
            shipment.getShipmentId()
        ));
        
        return trackingNumber;
    }
    
    @Override
    public TrackingInfo getTrackingInfo(String trackingNumber) {
        TrackingInfo info = new TrackingInfo();
        info.setTrackingUrl("https://www.dhl.com/track?trackingNumber=" + trackingNumber);
        info.setCurrentLocation("Hong Kong Hub");
        info.setLastUpdateMessage("Customs clearance completed");
        return info;
    }
    
    @Override
    public BigDecimal calculateShippingCost(ShippingAddress from, ShippingAddress to, double weight) {
        // DHL specializes in international - higher base cost
        BigDecimal baseCost = new BigDecimal("20.00");
        BigDecimal weightCost = new BigDecimal(weight * 3.0);
        return baseCost.add(weightCost);
    }
    
    @Override
    public boolean supportsAddress(ShippingAddress address) {
        // DHL ships worldwide
        return address != null && address.getCountry() != null;
    }
    
    @Override
    public int getEstimatedDeliveryDays(ShippingAddress from, ShippingAddress to) {
        // DHL is fast for international
        return from.getCountry().equals(to.getCountry()) ? 2 : 4;
    }
    
    @Override
    public boolean cancelShipment(String trackingNumber) {
        System.out.println("📦 [DHL] Cancelling shipment: " + trackingNumber);
        return true;
    }
}
