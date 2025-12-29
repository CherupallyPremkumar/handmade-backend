package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.risk.model.RiskAssessment;
import com.handmade.ecommerce.risk.model.RiskLevel;
import com.handmade.ecommerce.risk.service.RiskService;
import com.handmade.ecommerce.risk.service.RiskServiceException;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Orchestrator command that performs risk assessment.
 * 
 * Delegates to Risk module which calls external risk providers
 * (Sift, Riskified, Stripe Radar, etc.)
 * 
 * Risk Checks:
 * - AML (Anti-Money Laundering)
 * - CFT (Combating Financing of Terrorism)
 * - Fraud detection
 * - Sanctions screening
 * - Velocity checks (too many transactions)
 * 
 * Risk Levels:
 * - LOW: Proceed with payment
 * - MEDIUM: Manual review required (flag for review)
 * - HIGH: Reject payment immediately
 */
@Component
public class RiskCheckService implements Command<PaymentExchange> {

    private static final Logger log = LoggerFactory.getLogger(RiskCheckService.class);

    @Autowired
    private RiskService riskService; // ← Injected from Risk module

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {
            log.info("Performing risk assessment for buyer: {}",
                    context.getRequest().getBuyerId());

            // Call Risk module
            RiskAssessment assessment = riskService.assessRisk(
                    context.getRequest().getBuyerId(),
                    context.getRequest().getTotalAmount(),
                    context.getRequest().getCurrency(),
                    context.getRequest().getIpAddress(),
                    context.getRequest().getDeviceId());

            log.info("Risk assessment complete. Level: {}, Score: {}",
                    assessment.getRiskLevel(), assessment.getScore());

            // Evaluate risk level
            if (assessment.getRiskLevel() == RiskLevel.HIGH) {
                // High risk - reject payment
                throw new RiskServiceException(
                        "Payment rejected due to high fraud risk: " + assessment.getReason());
            }

            if (assessment.getRiskLevel() == RiskLevel.MEDIUM) {
                // Medium risk - flag for manual review
                log.warn("Payment flagged for manual review. Reason: {}", assessment.getReason());
                // TODO: Send to manual review queue
                // For now, we'll allow it to proceed but log the warning
            }

            // LOW risk - proceed with payment
            // Store risk score in context for audit trail
            context.setRiskScore(assessment.getScore());
            context.setRiskLevel(assessment.getRiskLevel().toString());

        } catch (RiskServiceException e) {
            log.error("Risk assessment failed: {}", e.getMessage());
            context.setException(e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during risk assessment", e);
            context.setException(e);
            throw e;
        }
    }
}