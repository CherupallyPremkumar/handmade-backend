package com.handmade.ecommerce.paymenttransaction.configuration.controller;

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
import com.handmade.ecommerce.payment.model.PaymentTransaction;

@RestController
@ChenileController(value = "paymenttransactionService", serviceName = "_paymenttransactionStateEntityService_",
		healthCheckerName = "paymenttransactionHealthChecker")
public class PaymentTransactionController extends ControllerSupport{
	
	@GetMapping("/paymenttransaction/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentTransaction>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/paymenttransaction")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentTransaction>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PaymentTransaction entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/paymenttransaction/{id}/{eventID}")
	@BodyTypeSelector("paymenttransactionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentTransaction>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
