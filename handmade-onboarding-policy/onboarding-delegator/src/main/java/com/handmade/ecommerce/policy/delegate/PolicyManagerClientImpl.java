package com.handmade.ecommerce.policy.delegate;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.service.OnboardingPolicyResolver;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Implementation of Policy Manager Client
 * Delegates calls to remote Policy Service via Chenile Proxy
 */
public class PolicyManagerClientImpl implements PolicyManagerClient {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("policyServiceProxy")
    private OnboardingPolicyResolver policyServiceProxy;

    @Override
    public OnboardingPolicy resolveOnboardingPolicy(String countryCode, SellerType sellerType) {
        logger.info("Resolving onboarding policy via delegate: country={}, sellerType={}", 
                   countryCode, sellerType);
        return policyServiceProxy.resolveActivePolicy(countryCode, sellerType);
    }

    @Override
    public OnboardingPolicy getPolicy(String id) {
        logger.debug("Retrieving policy: {}", id);
        // TODO: Implement when policy service has getById method
        throw new UnsupportedOperationException("Get policy by ID not yet implemented");
    }

    @Override
    public OnboardingPolicy getPolicyByVersion(String version) {
        logger.debug("Retrieving policy by version: {}", version);
        return policyServiceProxy.getPolicyByVersion(version);
    }

    @Override
    public List<OnboardingPolicy> getAllActivePolicies() {
        logger.debug("Retrieving all active policies");
        return policyServiceProxy.getAllActivePolicies();
    }

    @Override
    public boolean canOnboardInCountry(String countryCode, SellerType sellerType) {
        logger.debug("Checking if onboarding available: country={}, sellerType={}", 
                    countryCode, sellerType);
        return policyServiceProxy.canOnboardInCountry(countryCode, sellerType);
    }
}
