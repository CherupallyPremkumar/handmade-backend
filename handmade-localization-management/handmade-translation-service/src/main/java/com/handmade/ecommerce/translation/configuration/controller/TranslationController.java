package com.handmade.ecommerce.translation.configuration.controller;

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
import com.handmade.ecommerce.localization.model.Translation;

@RestController
@ChenileController(value = "translationService", serviceName = "_translationStateEntityService_",
		healthCheckerName = "translationHealthChecker")
public class TranslationController extends ControllerSupport{
	
	@GetMapping("/translation/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Translation>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/translation")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Translation>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Translation entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/translation/{id}/{eventID}")
	@BodyTypeSelector("translationBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Translation>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
