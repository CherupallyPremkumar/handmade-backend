package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.policy.domain.service.OnboardingPolicyService;
import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.VerifyIdentityPayload;
import com.handmade.ecommerce.kyc.api.KYCVerificationResult;
import com.handmade.ecommerce.kyc.api.KYCVerificationService;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/**
 * Action: Verify seller identity
 * Transition: IDENTITY_VERIFICATION → TAX_VERIFICATION
 * 
 * Week 1: Policy-driven verification
 * - Fetches rules from seller's locked policy
 * - Uses policy-specified provider and configuration
 * - Enforces retry limits from policy
 */
public class VerifyIdentityAction extends AbstractSTMTransitionAction<SellerAccount, VerifyIdentityPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(VerifyIdentityAction.class);
    
    @Autowired
    private OnboardingPolicyService policyService;
    
    @Autowired
    @Qualifier("manualKYCService") // Change to "stripeKYCService" for Phase 2
    private KYCVerificationService<SellerAccount> kycService;
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            VerifyIdentityPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        // 1. Load the rule for this step from seller's locked policy
        OnboardingPolicyRule rule = policyService
            .getRuleForStep(sellerAccount, "identity_verification")
            .orElseThrow(() -> new IllegalStateException(
                "No identity_verification rule in policy " + sellerAccount.onboardingPolicyVersion
            ));
        
        logger.info("Verifying identity for seller {} using policy {} (provider: {}, required: {})",
                   sellerAccount.id, sellerAccount.onboardingPolicyVersion, 
                   rule.getProviderType(), rule.isRequired());
        
        // 2. Check if step is required
        if (!rule.isRequired()) {
            logger.info("Identity verification not required for policy {}", 
                       sellerAccount.onboardingPolicyVersion);
            return; // Skip this step
        }
        
        // 3. Use provider configuration from policy
        String provider = rule.getProviderType(); // e.g., "manual", "stripe_identity", "onfido"
        Map<String, Object> config = rule.getProviderConfig();
        
        logger.info("Using KYC provider: {} with config: {}", provider, config);
        
        // 4. Execute verification
        String documentUploadId = sellerAccount.getDocumentUploadId(); // TODO: Add this field to SellerAccount
        
        KYCVerificationResult result = kycService.verifyIdentity(
            sellerAccount, 
            documentUploadId, 
            payload.getVerifiedBy()
        );
        
        // 5. Update seller state based on result
        if (result.isVerified()) {
            // Verification successful
            sellerAccount.setIdentityVerified(true);
            sellerAccount.setIdentityVerifiedAt(java.time.LocalDateTime.now());
            sellerAccount.setIdentityVerifiedBy(result.getVerifiedBy());
            sellerAccount.setKycProvider(result.getProvider());
            sellerAccount.setKycVerificationId(result.getVerificationId());
            sellerAccount.setKycConfidenceScore(result.getConfidenceScore());
            
            logger.info("Identity verified for seller {} by {} (confidence: {})", 
                       sellerAccount.id, result.getProvider(), result.getConfidenceScore());
        } else {
            // Verification failed - check retry limit from policy
            int attempts = sellerAccount.getIdentityVerificationAttempts();
            
            if (attempts >= rule.getMaxRetries()) {
                String message = String.format(
                    "Exceeded max retries (%d) for identity verification. Policy: %s",
                    rule.getMaxRetries(), sellerAccount.onboardingPolicyVersion
                );
                logger.error(message);
                throw new Exception(message);
            }
            
            sellerAccount.incrementIdentityVerificationAttempts();
            
            logger.warn("Identity verification failed for seller {} (attempt {}/{}): {}", 
                       sellerAccount.id, attempts + 1, rule.getMaxRetries(), 
                       result.getFailureReason());
            
            throw new Exception("Identity verification failed: " + result.getFailureReason());
        }
    }
}
