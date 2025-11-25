package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendWelcomeEmailCommandService extends BaseSellerOrchService {

    @Autowired
    private NotificationService notificationService;

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
        notificationService.sendSellerWelcomeEmail(seller);
        
        System.out.println("Welcome email sent to: " + seller.getContactEmail());
        super.doProcess(sellerContext);
    }
    
    private Seller getSeller(SellerContext context) {
        return (Seller) context.get("seller");
    }
}
