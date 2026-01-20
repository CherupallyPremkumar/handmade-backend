package com.handmade.ecommerce.eventqueue.configuration.controller;

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
import com.handmade.ecommerce.event.model.EventQueue;

@RestController
@ChenileController(value = "eventqueueService", serviceName = "_eventqueueStateEntityService_",
		healthCheckerName = "eventqueueHealthChecker")
public class EventQueueController extends ControllerSupport{
	
	@GetMapping("/eventqueue/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<EventQueue>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/eventqueue")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<EventQueue>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody EventQueue entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/eventqueue/{id}/{eventID}")
	@BodyTypeSelector("eventqueueBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<EventQueue>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
