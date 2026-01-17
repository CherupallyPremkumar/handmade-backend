package com.handmade.ecommerce.paymentorder.configuration.controller;

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
import com.handmade.ecommerce.payment.model.PaymentOrder;

@RestController
@ChenileController(value = "paymentorderService", serviceName = "_paymentorderStateEntityService_",
		healthCheckerName = "paymentorderHealthChecker")
public class PaymentOrderController extends ControllerSupport{
	
	@GetMapping("/paymentorder/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentOrder>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/paymentorder")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentOrder>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PaymentOrder entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/paymentorder/{id}/{eventID}")
	@BodyTypeSelector("paymentorderBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentOrder>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
