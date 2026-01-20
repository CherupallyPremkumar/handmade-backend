package com.handmade.ecommerce.promotion.configuration.controller;

import java.util.Map;

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
import com.handmade.ecommerce.promotion.model.Promotion;

@RestController
@ChenileController(value = "promotionService", serviceName = "_promotionStateEntityService_",
		healthCheckerName = "promotionHealthChecker")
public class PromotionController extends ControllerSupport{
	
	@GetMapping("/promotion/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Promotion>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/promotion")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Promotion>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Promotion entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/promotion/{id}/{eventID}")
	@BodyTypeSelector("promotionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Promotion>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
