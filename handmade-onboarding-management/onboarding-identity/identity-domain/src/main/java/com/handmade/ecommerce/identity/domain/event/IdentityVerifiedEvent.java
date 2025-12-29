package com.handmade.ecommerce.identity.domain.event;

import com.handmade.ecommerce.identity.domain.model.VerificationResult;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Domain Event published when identity is successfully verified.
 * This event allows decoupling the Identity module from Onboarding.
 */
@Getter
public class IdentityVerifiedEvent extends ApplicationEvent {
    private final String onboardingCaseId;
    private final VerificationResult result;

    public IdentityVerifiedEvent(Object source, String onboardingCaseId, VerificationResult result) {
        super(source);
        this.onboardingCaseId = onboardingCaseId;
        this.result = result;
    }
}
