package com.handmade.ecommerce.giftcard.configuration.controller;

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
import com.handmade.ecommerce.customer.model.GiftCard;

@RestController
@ChenileController(value = "giftcardService", serviceName = "_giftcardStateEntityService_",
		healthCheckerName = "giftcardHealthChecker")
public class GiftCardController extends ControllerSupport{
	
	@GetMapping("/giftcard/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<GiftCard>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/giftcard")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<GiftCard>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody GiftCard entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/giftcard/{id}/{eventID}")
	@BodyTypeSelector("giftcardBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<GiftCard>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
