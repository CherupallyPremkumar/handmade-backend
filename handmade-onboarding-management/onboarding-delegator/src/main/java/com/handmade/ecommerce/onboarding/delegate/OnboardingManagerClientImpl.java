package com.handmade.ecommerce.onboarding.delegate;

import com.handmade.ecommerce.onboarding.api.OnboardingCaseService;
import com.handmade.ecommerce.onboarding.dto.ActiveOnboardingCaseView;
import com.handmade.ecommerce.onboarding.dto.CreateSellerOnboardingCaseRequest;
import com.handmade.ecommerce.onboarding.dto.LockPolicyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OnboardingManagerClientImpl implements OnboardingManagerClient {

    @Autowired
    private OnboardingCaseService onboardingCaseService;

    @Override
    public boolean existsByEmail(String email) {
        return onboardingCaseService.existsByEmail(email);
    }

    @Override
    public String createCase(CreateSellerOnboardingCaseRequest request) {
        return onboardingCaseService.createCase(request);
    }

    @Override
    public Optional<ActiveOnboardingCaseView> findActiveCase(String email, String country) {
        return onboardingCaseService.findActiveCase(email, country);
    }

    @Override
    public void lockPolicy(String caseId, LockPolicyRequest lockPolicyRequest) {
        onboardingCaseService.lockPolicy(caseId, lockPolicyRequest);
    }
}
