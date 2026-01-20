package com.handmade.ecommerce.paymentrefundtransaction.configuration.controller;

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
import com.handmade.ecommerce.payment.model.PaymentRefundTransaction;

@RestController
@ChenileController(value = "paymentrefundtransactionService", serviceName = "_paymentrefundtransactionStateEntityService_",
		healthCheckerName = "paymentrefundtransactionHealthChecker")
public class PaymentRefundTransactionController extends ControllerSupport{
	
	@GetMapping("/paymentrefundtransaction/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentRefundTransaction>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/paymentrefundtransaction")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentRefundTransaction>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PaymentRefundTransaction entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/paymentrefundtransaction/{id}/{eventID}")
	@BodyTypeSelector("paymentrefundtransactionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentRefundTransaction>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
