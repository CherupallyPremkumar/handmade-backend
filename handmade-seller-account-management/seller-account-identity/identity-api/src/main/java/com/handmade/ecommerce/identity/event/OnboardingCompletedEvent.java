package com.handmade.ecommerce.identity.event;

import com.handmade.ecommerce.event.domain.BaseDomainEvent;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * OnboardingCompletedEvent - Triggered when a seller onboarding is successfully
 * approved
 */
@Getter
@ToString(callSuper = true)
public class OnboardingCompletedEvent extends BaseDomainEvent {

    private final String onboardingCaseId;
    private final String sellerId;
    private final String email;
    private final String country;
    private final LocalDateTime completedAt;

    public OnboardingCompletedEvent(String onboardingCaseId, String sellerId, String email, String country) {
        super("ONBOARDING_COMPLETED", sellerId);
        this.onboardingCaseId = onboardingCaseId;
        this.sellerId = sellerId;
        this.email = email;
        this.country = country;
        this.completedAt = LocalDateTime.now();
    }
}
