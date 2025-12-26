package com.handmade.ecommerce.seller.configuration.controller;

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
import com.handmade.ecommerce.seller.model.Seller;
import org.chenile.security.model.SecurityConfig;

@RestController
@ChenileController(value = "sellerService", serviceName = "_sellerStateEntityService_",
		healthCheckerName = "sellerHealthChecker")
public class SellerController extends ControllerSupport{
	
	@GetMapping("/seller/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Seller>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/seller")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Seller>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Seller entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/seller/{id}/{eventID}")
	@BodyTypeSelector("sellerBodyTypeSelector")
    @SecurityConfig(authoritiesSupplier = "sellerEventAuthoritiesSupplier")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Seller>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
