package com.handmade.ecommerce.orchestrator.api;

import com.handmade.ecommerce.ledger.service.LedgerService;
import com.handmade.ecommerce.orchestrator.configuration.controller.ProductOrchestratorController;
import com.handmade.ecommerce.payment.configuration.dao.PaymentOrderRepository;
import com.handmade.ecommerce.payment.dto.webhook.RazorpayWebhook;
import com.handmade.ecommerce.payment.dto.webhook.StripeWebhook;
import com.handmade.ecommerce.wallet.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Webhook Controller
 * Handles asynchronous payment notifications from PSPs
 */

@RestController
@ChenileController(value = "productOrchestratorService", serviceName = "_productOrchestratorService_",
        healthCheckerName = "productOrchestratorHealthChecker")
public class WebhookController extends ControllerSupport {


    private static final Logger log = Logger.getLogger(ProductOrchestratorController.class.getName());


    @PostMapping("/razorpay")
    public ResponseEntity<String> handleRazorpayWebhook(
            @RequestBody String payload,
            HttpServletRequest httpServletRequest,
            @RequestHeader("X-Razorpay-Signature") String signature) {
        log.info("Received Razorpay webhook");
        try {
            process(httpServletRequest,payload);
            return ResponseEntity.ok("SUCCESS");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("FAILED");
        }
    }

}
