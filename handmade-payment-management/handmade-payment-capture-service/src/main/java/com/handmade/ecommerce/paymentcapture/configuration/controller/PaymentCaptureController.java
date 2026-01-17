package com.handmade.ecommerce.paymentcapture.configuration.controller;

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
import com.handmade.ecommerce.payment.model.PaymentCapture;

@RestController
@ChenileController(value = "paymentcaptureService", serviceName = "_paymentcaptureStateEntityService_",
		healthCheckerName = "paymentcaptureHealthChecker")
public class PaymentCaptureController extends ControllerSupport{
	
	@GetMapping("/paymentcapture/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentCapture>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/paymentcapture")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentCapture>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PaymentCapture entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/paymentcapture/{id}/{eventID}")
	@BodyTypeSelector("paymentcaptureBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PaymentCapture>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
