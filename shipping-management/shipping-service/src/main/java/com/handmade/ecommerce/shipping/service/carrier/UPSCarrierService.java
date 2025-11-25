package com.handmade.ecommerce.shipping.service.carrier;

import com.handmade.ecommerce.shipping.model.Shipment;
import com.handmade.ecommerce.shipping.model.ShippingAddress;
import com.handmade.ecommerce.shipping.model.TrackingInfo;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * UPSCarrierService - UPS implementation
 */
@Service
public class UPSCarrierService extends AbstractCarrierService {
    
    @Override
    public String getCarrierCode() {
        return "UPS";
    }
    
    @Override
    public String getCarrierName() {
        return "UPS";
    }
    
    @Override
    protected String doCreateLabel(Shipment shipment, ShippingAddress address) {
        // TODO: Call UPS API
        String trackingNumber = "1Z" + System.currentTimeMillis();
        System.out.println("Creating UPS label for shipment: " + shipment.getShipmentId());
        return trackingNumber;
    }
    
    @Override
    protected TrackingInfo doGetTrackingInfo(String trackingNumber) {
        // TODO: Call UPS tracking API
        TrackingInfo info = new TrackingInfo();
        info.setTrackingUrl("https://ups.com/track/" + trackingNumber);
        info.setCurrentLocation("Louisville, KY");
        info.setLastUpdateMessage("Out for delivery");
        return info;
    }
    
    @Override
    protected BigDecimal doCalculateShippingCost(
        ShippingAddress from, 
        ShippingAddress to, 
        double weight
    ) {
        // UPS calculation: $12 base + $1.5 per kg
        return new BigDecimal("12.00")
            .add(new BigDecimal(weight * 1.5));
    }
}
