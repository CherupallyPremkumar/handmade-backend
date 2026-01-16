package com.handmade.ecommerce.adcampaign.configuration.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;

import org.chenile.stm.StateEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import com.handmade.ecommerce.adcampaign.model.AdCampaign;

@RestController
@ChenileController(value = "adCampaignService", serviceName = "_adCampaignStateEntityService_",
		healthCheckerName = "adCampaignHealthChecker")
public class AdCampaignController extends ControllerSupport{
	
	@GetMapping("/adCampaign/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<AdCampaign>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/adCampaign")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<AdCampaign>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody AdCampaign entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/adCampaign/{id}/{eventID}")
	@BodyTypeSelector("adCampaignBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<AdCampaign>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
