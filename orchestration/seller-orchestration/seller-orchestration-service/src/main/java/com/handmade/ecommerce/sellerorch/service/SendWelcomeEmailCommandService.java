package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.sellerorch.SellerContext;
import org.chenile.mqtt.pubsub.MqttPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendWelcomeEmailCommandService extends BaseSellerOrchService {

    @Autowired
    MqttPublisher mqttPublisher;

    @Override
    protected void doProcess(SellerContext sellerContext) {
        Seller seller = getSeller(sellerContext);
        
        if (seller == null) {
            throw new IllegalStateException("Seller not found in context");
        }
        
        // Validate seller email
        if (seller.getContactEmail() == null || seller.getContactEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Seller email is required to send welcome email");
        }
        
        // Send welcome email using notification service
        mqttPublisher.publish();
        
        System.out.println("Welcome email sent to: " + seller.getContactEmail());

    }
    
    private Seller getSeller(SellerContext context) {
        return (Seller) context.get("seller");
    }
}
