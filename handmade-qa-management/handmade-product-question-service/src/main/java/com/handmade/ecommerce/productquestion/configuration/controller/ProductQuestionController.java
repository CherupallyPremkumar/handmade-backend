package com.handmade.ecommerce.productquestion.configuration.controller;

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
import com.handmade.ecommerce.qa.model.ProductQuestion;

@RestController
@ChenileController(value = "productquestionService", serviceName = "_productquestionStateEntityService_",
		healthCheckerName = "productquestionHealthChecker")
public class ProductQuestionController extends ControllerSupport{
	
	@GetMapping("/productquestion/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductQuestion>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/productquestion")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductQuestion>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody ProductQuestion entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/productquestion/{id}/{eventID}")
	@BodyTypeSelector("productquestionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductQuestion>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
