package com.handmade.ecommerce.settlementbatch.configuration.controller;

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
import com.handmade.ecommerce.finance.model.SettlementBatch;

@RestController
@ChenileController(value = "settlementbatchService", serviceName = "_settlementbatchStateEntityService_",
		healthCheckerName = "settlementbatchHealthChecker")
public class SettlementBatchController extends ControllerSupport{
	
	@GetMapping("/settlementbatch/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SettlementBatch>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/settlementbatch")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SettlementBatch>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody SettlementBatch entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/settlementbatch/{id}/{eventID}")
	@BodyTypeSelector("settlementbatchBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SettlementBatch>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
