package com.handmade.ecommerce.seller.onboarding.service.impl;

import com.handmade.ecommerce.seller.onboarding.api.SellerOnboardingService;
import com.handmade.ecommerce.seller.onboarding.api.dto.OnboardingResponse;
import com.handmade.ecommerce.seller.onboarding.api.dto.OnboardingStepResponse;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingStep;
import com.handmade.ecommerce.seller.onboarding.repository.SellerOnboardingStepRepository;
import com.handmade.ecommerce.seller.onboarding.service.commands.OnboardingResumeContext;

import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.List;
import java.util.stream.Collectors;

public class SellerOnboardingServiceImpl extends StateEntityServiceImpl<SellerOnboardingCase>
        implements SellerOnboardingService {

    @Autowired
    @Qualifier("onboardingResumeExecutor")
    private OrchExecutor<OnboardingResumeContext> orchestrator;

    @Autowired
    private SellerOnboardingStepRepository stepRepository;

    public SellerOnboardingServiceImpl(STM<SellerOnboardingCase> stm,
            STMActionsInfoProvider stmActionsInfoProvider,
            EntityStore<SellerOnboardingCase> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    public void handleStripeWebHook(String payload) {
        try {
            // In a real scenario, we would parse the caseId from the Stripe payload
            // (metadata)
            // For now, we logging it to satisfy the Chenile service validation
            System.out.println("Received Stripe Webhook: " + payload);
            // orchestrator.execute(new OnboardingResumeContext(caseId, payload));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StateEntityServiceResponse<SellerOnboardingCase> create(SellerOnboardingCase sellerOnboardingCase) {

        // what every we can do to validate the seller onboarding case create will
        // create the seller onboarding case
        return super.create(sellerOnboardingCase);
    }

    @Override
    public OnboardingResponse getOnboarding(String caseId) {
        SellerOnboardingCase entity = super.retrieve(caseId).getMutatedEntity();
        return mapToResponse(entity);
    }

    @Override
    public List<OnboardingStepResponse> getSteps(String caseId) {
        return stepRepository.findByOnboardingCaseId(caseId).stream()
                .map(this::mapToStepResponse)
                .collect(Collectors.toList());
    }

    private OnboardingResponse mapToResponse(SellerOnboardingCase entity) {
        if (entity == null)
            return null;
        return OnboardingResponse.builder()
                .id(entity.getId())
                .sellerId(entity.getSellerId())
                .email(entity.getEmail())
                .businessName(entity.getBusinessName())
                .businessType(entity.getBusinessType())
                .state(entity.getCurrentState() != null ? entity.getCurrentState().getStateId() : null)
                .completionPercentage(entity.getCompletionPercentage())
                .startedAt(entity.getStartedAt())
                .completedAt(entity.getCompletedAt())
                .steps(entity.getSteps() != null ? entity.getSteps().stream()
                        .map(this::mapToStepResponse)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    private OnboardingStepResponse mapToStepResponse(SellerOnboardingStep step) {
        if (step == null)
            return null;
        return OnboardingStepResponse.builder()
                .stepName(step.getStepName())
                .status(step.getStatus())
                .providerRef(step.getProviderRef())
                .lastUpdated(step.getLastUpdated())
                .metadata(step.getMetadata())
                .build();
    }
}
