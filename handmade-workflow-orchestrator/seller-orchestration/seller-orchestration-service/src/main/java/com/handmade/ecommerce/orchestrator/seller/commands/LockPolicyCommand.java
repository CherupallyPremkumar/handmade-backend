package com.handmade.ecommerce.orchestrator.seller.commands;

import com.handmade.ecommerce.seller.delegate.SellerManagerClient;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Locks policy version to seller
 * Spring bean name: lock-policy-command
 */
@Component("lock-policy-command")
public class LockPolicyCommand implements Command<Map<String, Object>> {
    
    private final SellerManagerClient sellerManagerClient;
    
    public LockPolicyCommand(SellerManagerClient sellerManagerClient) {
        this.sellerManagerClient = sellerManagerClient;
    }
    
    @Override
    public void execute(Map<String, Object> context) throws Exception {
        String sellerId = (String) context.get("sellerId");
        String policyVersion = (String) ((Map<?, ?>) context.get("onboardingPolicy")).get("version");
        
        // Lock policy version to seller
        sellerManagerClient.lockPolicyVersion(sellerId, policyVersion);
    }
}
