package com.handmade.ecommerce.seller.account.infrastructure.rest;

import com.handmade.ecommerce.seller.account.api.SellerOnboardingService;
import com.handmade.ecommerce.seller.account.dto.StartOnboardingRequest;
import com.handmade.ecommerce.seller.account.dto.StartOnboardingResponse;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * REST Controller for Seller Onboarding Orchestration.
 * Entry point for starting the onboarding flow.
 */
@RestController
@ChenileController(value = "sellerOnboardingController", serviceName = "sellerOnboardingService")
public class SellerOnboardingController extends ControllerSupport {

    /**
     * POST /seller-onboarding/start
     * 
     * Initiates the seller onboarding process.
     * Resolves policy, checks for existing cases, and creates a new onboarding case
     * if needed.
     */
    @PostMapping("/seller-onboarding/start")
    public ResponseEntity<GenericResponse<StartOnboardingResponse>> startOnboarding(
            HttpServletRequest httpServletRequest,
            @RequestBody @ChenileParamType(StartOnboardingRequest.class) StartOnboardingRequest request) {
        return process(httpServletRequest, request);
    }
}
