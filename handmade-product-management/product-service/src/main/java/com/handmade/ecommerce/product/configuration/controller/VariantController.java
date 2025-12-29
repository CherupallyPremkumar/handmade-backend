package com.handmade.ecommerce.product.configuration.controller;

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
import com.handmade.ecommerce.product.domain.model.Variant;


@RestController
@ChenileController(value = "variantService", serviceName = "_variantStateEntityService_",
		healthCheckerName = "variantHealthChecker")
public class VariantController extends ControllerSupport{
	
	@GetMapping("/variant/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Variant>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/variant")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Variant>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Variant entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/variant/{id}/{eventID}")
	@BodyTypeSelector("variantBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Variant>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
