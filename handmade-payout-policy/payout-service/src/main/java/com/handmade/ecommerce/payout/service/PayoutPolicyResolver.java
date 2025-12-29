package com.handmade.ecommerce.payout.service;

import com.handmade.ecommerce.payout.domain.aggregate.PayoutPolicy;
import com.handmade.ecommerce.payout.repository.PayoutPolicyRepository;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for resolving payout policies
 */
@Service
@Transactional(readOnly = true)
public class PayoutPolicyResolver {

    private static final Logger logger = LoggerFactory.getLogger(PayoutPolicyResolver.class);

    @Autowired
    private PayoutPolicyRepository policyRepository;

    /**
     * Resolve active policy for country and seller type
     * Called when seller receives first payout
     */
    public PayoutPolicy resolveActivePolicy(String countryCode, SellerType sellerType) {
        logger.info("Resolving active payout policy for country={}, sellerType={}",
                countryCode, sellerType);

        LocalDate today = LocalDate.now();
        PayoutPolicy policy = policyRepository.findActivePolicy(countryCode, sellerType, today)
                .orElseThrow(() -> new PolicyNotFoundException(
                        String.format("No active payout policy found for %s %s",
                                countryCode, sellerType)));

        logger.info("Resolved policy: version={}, frequency={}",
                policy.getPolicyVersion(), policy.getDefaultFrequency());
        return policy;
    }

    /**
     * Get policy by version (for sellers locked to a specific version)
     */
    public PayoutPolicy getPolicyByVersion(String policyVersion) {
        logger.info("Fetching payout policy by version={}", policyVersion);

        return policyRepository.findByPolicyVersion(policyVersion)
                .orElseThrow(() -> new PolicyNotFoundException(
                        "Payout policy not found for version: " + policyVersion));
    }

    /**
     * Check if payout is available for country and seller type
     */
    public boolean canPayoutInCountry(String countryCode, SellerType sellerType) {
        LocalDate today = LocalDate.now();
        boolean hasPolicy = policyRepository.hasActivePolicy(countryCode, sellerType, today);

        logger.info("Payout availability check: country={}, sellerType={}, available={}",
                countryCode, sellerType, hasPolicy);

        return hasPolicy;
    }

    /**
     * Get all active policies
     */
    public List<PayoutPolicy> getAllActivePolicies() {
        return policyRepository.findAllActive();
    }

    /**
     * Get all draft policies
     */
    public List<PayoutPolicy> getAllDraftPolicies() {
        return policyRepository.findAllDrafts();
    }

    /**
     * Get all deprecated policies
     */
    public List<PayoutPolicy> getAllDeprecatedPolicies() {
        return policyRepository.findAllDeprecated();
    }

    public static class PolicyNotFoundException extends RuntimeException {
        public PolicyNotFoundException(String message) {
            super(message);
        }
    }
}
