package com.handmade.ecommerce.orchestration.product.controller;

import com.handmade.ecommerce.orchestration.product.dto.ProductOrchestrationRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Chenile Controller for Product Orchestration.
 */
@RestController
@ChenileController(value = "productOrchestrationController", serviceName = "_productOrchestrationService_")
public class ProductOrchestrationController extends ControllerSupport {

    @PostMapping("/products/orchestrate")
    public ResponseEntity<GenericResponse<ProductResponse>> orchestrateProduct(
            HttpServletRequest httpServletRequest,
            @RequestBody @ChenileParamType(ProductOrchestrationRequest.class) ProductOrchestrationRequest request) {
        return process(httpServletRequest, "orchestrateProduct", request);
    }
}
