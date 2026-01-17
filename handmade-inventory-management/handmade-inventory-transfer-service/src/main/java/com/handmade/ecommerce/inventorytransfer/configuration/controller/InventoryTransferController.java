package com.handmade.ecommerce.inventorytransfer.configuration.controller;

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
import com.handmade.ecommerce.inventory.model.InventoryTransfer;

@RestController
@ChenileController(value = "inventorytransferService", serviceName = "_inventorytransferStateEntityService_",
		healthCheckerName = "inventorytransferHealthChecker")
public class InventoryTransferController extends ControllerSupport{
	
	@GetMapping("/inventorytransfer/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InventoryTransfer>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/inventorytransfer")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InventoryTransfer>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody InventoryTransfer entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/inventorytransfer/{id}/{eventID}")
	@BodyTypeSelector("inventorytransferBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InventoryTransfer>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
