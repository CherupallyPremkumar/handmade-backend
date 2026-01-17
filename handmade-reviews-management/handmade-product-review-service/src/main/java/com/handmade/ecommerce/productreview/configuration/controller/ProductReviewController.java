package com.handmade.ecommerce.productreview.configuration.controller;

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
import com.handmade.ecommerce.reviews.model.ProductReview;

@RestController
@ChenileController(value = "productreviewService", serviceName = "_productreviewStateEntityService_",
		healthCheckerName = "productreviewHealthChecker")
public class ProductReviewController extends ControllerSupport{
	
	@GetMapping("/productreview/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductReview>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/productreview")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductReview>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody ProductReview entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/productreview/{id}/{eventID}")
	@BodyTypeSelector("productreviewBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ProductReview>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
