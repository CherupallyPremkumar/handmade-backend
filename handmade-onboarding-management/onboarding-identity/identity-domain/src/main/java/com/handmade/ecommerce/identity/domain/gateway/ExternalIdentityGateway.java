package com.handmade.ecommerce.identity.domain.gateway;

import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;
import com.handmade.ecommerce.identity.domain.model.ExternalVerificationSession;

/**
 * Interface for external identity verification provider (e.g., Stripe, Onfido).
 * This interface resides in the domain to enforce dependency inversion.
 * Infrastructure layer implements this using specific vendor SDKs.
 */
public interface ExternalIdentityGateway {

    /**
     * Create an external verification session for the given local session.
     */
    ExternalVerificationSession createVerificationSession(IdentityVerificationSession session);

    /**
     * Check if the gateway is correctly configured and ready to use.
     */
    boolean isConfigured();
}
