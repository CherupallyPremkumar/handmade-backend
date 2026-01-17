package com.handmade.ecommerce.selleraccount.configuration.controller;

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
import com.handmade.ecommerce.seller.model.SellerAccount;

@RestController
@ChenileController(value = "selleraccountService", serviceName = "_selleraccountStateEntityService_",
		healthCheckerName = "selleraccountHealthChecker")
public class SellerAccountController extends ControllerSupport{
	
	@GetMapping("/selleraccount/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerAccount>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/selleraccount")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerAccount>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody SellerAccount entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/selleraccount/{id}/{eventID}")
	@BodyTypeSelector("selleraccountBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerAccount>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
