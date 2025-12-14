package com.handmade.ecommerce.productorch.configuration.controller;

import com.handmade.ecommerce.productorch.service.ProductContext;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "productorchService", serviceName = "_productorchService_",
		healthCheckerName = "productorchHealthChecker")
public class ProductorchController extends ControllerSupport{
	
    @PostMapping("/create")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<ProductContext>> create(
        HttpServletRequest httpServletRequest,
        @RequestBody ProductContext productContext){
        return process(httpServletRequest,productContext);
    }

}
