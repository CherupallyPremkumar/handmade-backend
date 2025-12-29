package com.handmade.ecommerce.shipping.carrier.ups;

import com.handmade.ecommerce.shipping.core.CarrierPlugin;
import com.handmade.ecommerce.shipping.model.Shipment;
import com.handmade.ecommerce.shipping.model.ShippingAddress;
import com.handmade.ecommerce.shipping.model.TrackingInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * UPSCarrierPlugin - UPS implementation as a plugin
 * This is a SEPARATE MODULE that can be deployed independently
 */
@Component
public class UPSCarrierPlugin implements CarrierPlugin {
    
    @Value("${carrier.ups.enabled:true}")
    private boolean enabled;
    
    @Value("${carrier.ups.api.key:}")
    private String apiKey;
    
    @Override
    public String getCarrierCode() {
        return "UPS";
    }
    
    @Override
    public String getCarrierName() {
        return "UPS";
    }
    
    @Override
    public String getDescription() {
        return "UPS - United Parcel Service";
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public String createShippingLabel(Shipment shipment, ShippingAddress address) {
        // UPS tracking numbers start with "1Z"
        String trackingNumber = "1Z" + System.currentTimeMillis();
        
        System.out.println(String.format(
            "📦 [UPS] Creating label for shipment %s",
            shipment.getShipmentId()
        ));
        
        return trackingNumber;
    }
    
    @Override
    public TrackingInfo getTrackingInfo(String trackingNumber) {
        TrackingInfo info = new TrackingInfo();
        info.setTrackingUrl("https://www.ups.com/track?tracknum=" + trackingNumber);
        info.setCurrentLocation("Louisville, KY Hub");
        info.setLastUpdateMessage("Out for delivery");
        return info;
    }
    
    @Override
    public BigDecimal calculateShippingCost(ShippingAddress from, ShippingAddress to, double weight) {
        BigDecimal baseCost = new BigDecimal("12.00");
        BigDecimal weightCost = new BigDecimal(weight * 2.0);
        return baseCost.add(weightCost);
    }
    
    @Override
    public boolean supportsAddress(ShippingAddress address) {
        // UPS ships worldwide
        return address != null && address.getCountry() != null;
    }
    
    @Override
    public int getEstimatedDeliveryDays(ShippingAddress from, ShippingAddress to) {
        return from.getCountry().equals(to.getCountry()) ? 2 : 5;
    }
    
    @Override
    public boolean cancelShipment(String trackingNumber) {
        System.out.println("📦 [UPS] Cancelling shipment: " + trackingNumber);
        return true;
    }
}
