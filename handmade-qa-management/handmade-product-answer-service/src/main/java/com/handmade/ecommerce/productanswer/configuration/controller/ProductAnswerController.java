package com.handmade.ecommerce.productanswer.configuration.controller;

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
import com.handmade.ecommerce.qa.model.ProductAnswer;

@RestController
@ChenileController(value = "productanswerService", serviceName = "_productanswerStateEntityService_",
		healthCheckerName = "productanswerHealthChecker")
public class ProductAnswerController extends ControllerSupport{
	
	@GetMapping("/productanswer/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductAnswer>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/productanswer")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductAnswer>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody ProductAnswer entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/productanswer/{id}/{eventID}")
	@BodyTypeSelector("productanswerBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductAnswer>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
