package com.handmade.ecommerce.orchestrator.seller.commands;

import com.handmade.ecommerce.seller.delegate.SellerManagerClient;
import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import com.handmade.ecommerce.seller.dto.CreateSellerRequest;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Creates seller in DRAFT state
 * Spring bean name: create-seller-command
 * 
 * This MUST run before lock-policy-command
 */
@Component("create-seller-command")
public class CreateSellerCommand implements Command<Map<String, Object>> {
    
    private final SellerManagerClient sellerManagerClient;
    
    public CreateSellerCommand(SellerManagerClient sellerManagerClient) {
        this.sellerManagerClient = sellerManagerClient;
    }
    
    @Override
    public void execute(Map<String, Object> context) throws Exception {
        // Extract seller data from context
        String email = (String) context.get("email");
        String businessName = (String) context.get("businessName");
        String countryCode = (String) context.get("countryCode");
        String sellerTypeStr = (String) context.get("sellerType");
        
        // Build request
        CreateSellerRequest request = new CreateSellerRequest();
        request.setEmail(email);
        request.setBusinessName(businessName);
        request.setCountryCode(countryCode);
        request.setSellerType(SellerType.valueOf(sellerTypeStr));
        
        // Create seller in DRAFT state
        SellerAccount account = sellerManagerClient.registerSellerAccount(request);
        
        // Store sellerId in context for subsequent steps
        context.put("sellerId", account.getId());
        context.put("sellerAccount", account);
    }
}
