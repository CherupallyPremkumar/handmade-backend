package com.handmade.ecommerce.shippinglabel.configuration.controller;

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
import com.handmade.ecommerce.logistics.model.ShippingLabel;

@RestController
@ChenileController(value = "shippinglabelService", serviceName = "_shippinglabelStateEntityService_",
		healthCheckerName = "shippinglabelHealthChecker")
public class ShippingLabelController extends ControllerSupport{
	
	@GetMapping("/shippinglabel/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ShippingLabel>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/shippinglabel")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ShippingLabel>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody ShippingLabel entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/shippinglabel/{id}/{eventID}")
	@BodyTypeSelector("shippinglabelBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ShippingLabel>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
