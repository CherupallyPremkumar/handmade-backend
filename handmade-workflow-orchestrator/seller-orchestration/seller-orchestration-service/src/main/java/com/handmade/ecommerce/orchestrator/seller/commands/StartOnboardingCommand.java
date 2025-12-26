package com.handmade.ecommerce.orchestrator.seller.commands;

import com.handmade.ecommerce.seller.delegate.SellerManagerClient;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Transitions seller to ONBOARDING state
 * Spring bean name: start-onboarding-command
 */
@Component("start-onboarding-command")
public class StartOnboardingCommand implements Command<Map<String, Object>> {
    
    private final SellerManagerClient sellerManagerClient;
    
    public StartOnboardingCommand(SellerManagerClient sellerManagerClient) {
        this.sellerManagerClient = sellerManagerClient;
    }
    
    @Override
    public void execute(Map<String, Object> context) throws Exception {
        String sellerId = (String) context.get("sellerId");
        
        // Transition to ONBOARDING state
        sellerManagerClient.transitionState(sellerId, "start_onboarding");
    }
}
