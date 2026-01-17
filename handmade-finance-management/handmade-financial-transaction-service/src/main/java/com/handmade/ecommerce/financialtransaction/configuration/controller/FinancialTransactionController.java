package com.handmade.ecommerce.financialtransaction.configuration.controller;

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
import com.handmade.ecommerce.finance.model.FinancialTransaction;

@RestController
@ChenileController(value = "financialtransactionService", serviceName = "_financialtransactionStateEntityService_",
		healthCheckerName = "financialtransactionHealthChecker")
public class FinancialTransactionController extends ControllerSupport{
	
	@GetMapping("/financialtransaction/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FinancialTransaction>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/financialtransaction")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FinancialTransaction>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody FinancialTransaction entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/financialtransaction/{id}/{eventID}")
	@BodyTypeSelector("financialtransactionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FinancialTransaction>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
