package com.handmade.ecommerce.offer.configuration.controller;

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
import com.handmade.ecommerce.offer.model.Offer;

@RestController
@ChenileController(value = "offerService", serviceName = "_offerStateEntityService_",
		healthCheckerName = "offerHealthChecker")
public class OfferController extends ControllerSupport{
	
	@GetMapping("/offer/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Offer>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/offer")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Offer>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Offer entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/offer/{id}/{eventID}")
	@BodyTypeSelector("offerBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Offer>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
