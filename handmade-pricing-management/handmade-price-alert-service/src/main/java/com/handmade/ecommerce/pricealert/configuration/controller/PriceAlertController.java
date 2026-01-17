package com.handmade.ecommerce.pricealert.configuration.controller;

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
import com.handmade.ecommerce.pricing.model.PriceAlert;

@RestController
@ChenileController(value = "pricealertService", serviceName = "_pricealertStateEntityService_",
		healthCheckerName = "pricealertHealthChecker")
public class PriceAlertController extends ControllerSupport{
	
	@GetMapping("/pricealert/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PriceAlert>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/pricealert")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PriceAlert>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PriceAlert entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/pricealert/{id}/{eventID}")
	@BodyTypeSelector("pricealertBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PriceAlert>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
