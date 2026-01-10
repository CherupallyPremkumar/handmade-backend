package com.handmade.ecommerce.seller.onboarding.configuration.controller;

import com.handmade.ecommerce.seller.onboarding.api.SellerOnboardingService;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingStep;
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
@ChenileController(value = "sellerOnboardingService", serviceName = "_sellerOnboardingService_", healthCheckerName = "sellerOnboardingHealthChecker")
public class SellerOnboardingController extends ControllerSupport {

	@PostMapping("/seller-onboarding")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerOnboardingCase>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class) @RequestBody SellerOnboardingCase entity) {
		return process(httpServletRequest, entity);
	}

	@PatchMapping("/seller-onboarding/{id}/{eventID}")
	@BodyTypeSelector("sellerOnboardingBodyTypeSelector")
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
