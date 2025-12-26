package com.handmade.ecommerce.seller.service;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.service.OnboardingPolicyResolver;
import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.InitializeOnboardingPayload;
import com.handmade.ecommerce.seller.repository.SellerAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Seller Onboarding Initialization Service
 * 
 * Week 1: Locks sellers to active policy version at onboarding start
 */
@Service
public class SellerOnboardingService {
    
    private static final Logger logger = LoggerFactory.getLogger(SellerOnboardingService.class);
    
    @Autowired
    private OnboardingPolicyResolver policyResolver;
    
    @Autowired
    private SellerAccountRepository sellerRepository;
    
    /**
     * Initialize seller onboarding
     * 
     * Flow:
     * 1. Resolve active policy for country/seller type
     * 2. Create SellerAccount
     * 3. Lock seller to policy (IMMUTABLE)
     * 4. Set initial state to DRAFT
     * 
     * @param request Onboarding initialization request
     * @return Created seller account locked to policy
     * @throws com.handmade.ecommerce.platform.service.policy.NoActivePolicyException if no active policy exists
     */
    @Transactional
    public SellerAccount initializeOnboarding(InitializeOnboardingPayload request) {
        
        logger.info("Initializing onboarding for email={}, country={}, sellerType={}", 
                   request.getEmail(), request.getCountryCode(), request.getSellerType());
        
        // 1. Resolve active policy
        OnboardingPolicy policy = policyResolver.resolveActivePolicy(
            request.getCountryCode(),
            request.getSellerType()
        );
        
        logger.info("Resolved policy: version={}, id={}", policy.getVersion(), policy.getId());
        
        // 2. Create seller account
        SellerAccount seller = new SellerAccount();
        seller.id = generateSellerId();
        seller.email = request.getEmail();
        seller.businessName = request.getBusinessName();
        seller.platformId = request.getPlatformId();
        
        // 3. LOCK TO POLICY (immutable - set once, never changed)
        seller.onboardingPolicyId = policy.getId();
        seller.onboardingPolicyVersion = policy.getVersion();
        seller.policyLockedAt = LocalDateTime.now();
        
        logger.info("Seller {} locked to policy version {} at {}", 
                   seller.id, policy.getVersion(), seller.policyLockedAt);
        
        // 4. Set initial state
        seller.setStateId("DRAFT");
        seller.setStateEntryTime(LocalDateTime.now());
        
        // 5. Initialize flags
        seller.verified = false;
        seller.kycCompleted = false;
        seller.suspended = false;
        
        // 6. Set timestamps
        seller.createdAt = LocalDateTime.now();
        seller.updatedAt = LocalDateTime.now();
        
        // 7. Save
        SellerAccount savedSeller = sellerRepository.save(seller);
        
        logger.info("Seller onboarding initialized: id={}, policyVersion={}, state={}", 
                   savedSeller.id, savedSeller.onboardingPolicyVersion, savedSeller.getStateId());
        
        return savedSeller;
    }
    
    /**
     * Generate unique seller ID
     */
    private String generateSellerId() {
        return "seller_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
    
    /**
     * Check if seller can start onboarding in their country
     */
    public boolean canOnboardInCountry(String countryCode, String sellerType) {
        try {
            return policyResolver.hasActivePolicy(
                countryCode, 
                com.handmade.ecommerce.seller.domain.enums.SellerType.valueOf(sellerType)
            );
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid seller type: {}", sellerType);
            return false;
        }
    }
}
