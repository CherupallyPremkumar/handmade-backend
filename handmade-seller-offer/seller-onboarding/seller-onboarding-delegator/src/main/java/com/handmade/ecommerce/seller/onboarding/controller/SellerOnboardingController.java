package com.handmade.ecommerce.seller.onboarding.controller;

import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingStep;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.chenile.stm.StateEntity;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@ChenileController(value = "onboardingService", serviceName = "_sellerOnboardingService_", healthCheckerName = "onboardingHealthChecker")
public class SellerOnboardingController extends ControllerSupport {

	@PostMapping("/seller-onboarding")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerOnboardingCase>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class) @RequestBody SellerOnboardingCase entity) {
		return process(httpServletRequest, entity);
	}

	@PostMapping("/webhook/stripe")
	public ResponseEntity<String> handleStripeWebHook(
			HttpServletRequest request,
			@ChenileParamType(Object.class) @RequestBody String payload) {
		super.process(request, payload);
		return ResponseEntity.ok("Received");
	}

	@PatchMapping("/seller-onboarding/{id}/{eventID}")
	@BodyTypeSelector("onboardingBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerOnboardingCase>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) @RequestBody String eventPayload) {
		return process(httpServletRequest, id, eventID, eventPayload);
	}

	@GetMapping("/seller-onboarding/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerOnboardingCase>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id) {
		return process(httpServletRequest, id);
	}

	@GetMapping("/seller-onboarding/{id}/steps")
	public ResponseEntity<GenericResponse<List<SellerOnboardingStep>>> getSteps(HttpServletRequest httpServletRequest,
			@PathVariable String id) {
		return process(httpServletRequest, id);
	}

}
