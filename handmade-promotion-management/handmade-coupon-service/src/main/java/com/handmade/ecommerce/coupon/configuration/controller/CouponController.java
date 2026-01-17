package com.handmade.ecommerce.coupon.configuration.controller;

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
import com.handmade.ecommerce.promotion.model.Coupon;

@RestController
@ChenileController(value = "couponService", serviceName = "_couponStateEntityService_",
		healthCheckerName = "couponHealthChecker")
public class CouponController extends ControllerSupport{
	
	@GetMapping("/coupon/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Coupon>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/coupon")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Coupon>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Coupon entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/coupon/{id}/{eventID}")
	@BodyTypeSelector("couponBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Coupon>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
