package com.handmade.ecommerce.seller.onboarding.configuration.controller.webhook;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/stripe")
@ChenileController(value = "sellerOnboardingService", serviceName = "_sellerOnboardingService_", healthCheckerName = "sellerOnboardingHealthChecker")
public class StripeWebhookController  extends ControllerSupport{

    @PostMapping
    public ResponseEntity<String> handleStripeWebHook(
            HttpServletRequest request,
            @RequestBody String payload) {
         super.process(request,payload);
        return ResponseEntity.ok("Received");
    }
}
