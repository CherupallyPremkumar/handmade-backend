package com.handmade.ecommerce.orchestrator.seller.configuration.controller;

import com.handmade.ecommerce.orchestrator.seller.context.StartOnboardingContext;
import com.handmade.ecommerce.orchestrator.seller.dto.StartOnboardingRequest;
import com.handmade.ecommerce.orchestrator.seller.dto.StartOnboardingResponse;
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
 * Chenile Controller for Start Seller Onboarding API.
 * Uses OWIZ flow: start-seller-onboarding.xml
 */
@RestController
@ChenileController(value = "sellerOnboardingController", serviceName = "_sellerOnboardingService_")
public class SellerOnboardingController extends ControllerSupport {

  /**
   * POST /seller-onboarding/start
   * 
   * Entry point when user clicks "Become Seller".
   * Executes OWIZ flow: start-seller-onboarding
   */
  @PostMapping("/seller-onboarding/start")
  public ResponseEntity<GenericResponse<StartOnboardingResponse>> startOnboarding(
      HttpServletRequest httpServletRequest,
      @RequestBody @ChenileParamType(StartOnboardingRequest.class) StartOnboardingRequest request) {
    return process(httpServletRequest, "startOnboarding", request);

  }
}
