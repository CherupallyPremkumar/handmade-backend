package com.handmade.ecommerce.inventory.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.inventory.service.InventoryService;
import com.handmade.ecommerce.inventory.service.impl.InventoryServiceImpl;
import com.handmade.ecommerce.inventory.service.healthcheck.InventoryHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class InventoryConfiguration {
	@Bean public InventoryService _inventoryService_() {
		return new InventoryServiceImpl();
	}

	@Bean InventoryHealthChecker inventoryHealthChecker(){
    	return new InventoryHealthChecker();
    }
}
