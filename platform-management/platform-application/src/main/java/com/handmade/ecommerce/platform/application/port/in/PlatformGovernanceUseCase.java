package com.handmade.ecommerce.platform.application.port.in;

import com.handmade.ecommerce.platform.application.command.*;

/**
 * Input port defining all platform governance use cases.
 * This is the contract that the application layer exposes.
 */
public interface PlatformGovernanceUseCase {
    
    /**
     * Activate the platform (Day 0 operation).
     */
    void activatePlatform(ActivatePlatformCommand command);
    
    /**
     * Revise commission structure.
     */
    void reviseCommission(ReviseCommissionCommand command);
    
    /**
     * Lock/restrict the platform.
     */
    void lockPlatform(LockPlatformCommand command);
    
    /**
     * Update brand identity.
     */
    void updateBrandIdentity(UpdateBrandIdentityCommand command);
    
    /**
     * Publish legal terms.
     */
    void publishLegalTerms(PublishLegalTermsCommand command);
}
