package com.handmade.ecommerce.orchestration.seller.application.service;

import com.handmade.ecommerce.onboarding.exception.DuplicateOnboardingCaseException;
import com.handmade.ecommerce.orchestration.seller.api.SellerOnboardingService;
import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;
import com.handmade.ecommerce.orchestration.seller.dto.StartOnboardingRequest;
import com.handmade.ecommerce.orchestration.seller.dto.StartOnboardingResponse;
import com.handmade.ecommerce.orchestration.seller.application.service.exception.ConflictException;
import com.handmade.ecommerce.orchestration.seller.application.service.exception.OrchestrationException;
import com.handmade.ecommerce.policy.exception.PolicyNotFoundException;
import org.chenile.base.exception.BadRequestException;
import org.chenile.owiz.OrchExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the SellerOnboardingService.
 * Coordinates the onboarding flow using the OWIZ orchestration engine.
 */
@Service("sellerOnboardingService")
public class SellerOnboardingServiceImpl implements SellerOnboardingService {

    @Autowired
    private OrchExecutor<SellerOnboardingContext> executor;

    @Override
    public StartOnboardingResponse startOnboarding(StartOnboardingRequest request) {
        try {
            SellerOnboardingContext context = this.makeContext(request);
            executor.execute(context);
            return context.getResponse();
        } catch (PolicyNotFoundException e) {
            throw new BadRequestException("Unsupported seller country/type");
        } catch (DuplicateOnboardingCaseException e) {
            throw new ConflictException("Onboarding already in progress for this email/country");
        } catch (OrchestrationException e) {
            throw e;
        } catch (Exception ex) {
            throw new OrchestrationException("Onboarding orchestration failed", ex);
        }
    }

    /**
     * Maps the public request DTO to the internal workflow context.
     */
    private SellerOnboardingContext makeContext(StartOnboardingRequest request) {
        SellerOnboardingContext context = new SellerOnboardingContext();
        context.setEmail(request.email());
        context.setCountry(request.country());
        context.setSellerType(request.sellerType());

        // Initialize the response DTO within the context
        StartOnboardingResponse response = new StartOnboardingResponse();
        context.setResponse(response);

        return context;
    }
}
