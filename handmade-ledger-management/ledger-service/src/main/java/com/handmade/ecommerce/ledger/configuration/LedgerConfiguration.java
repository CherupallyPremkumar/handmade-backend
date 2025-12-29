package com.handmade.ecommerce.ledger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.ledger.service.LedgerService;
import com.handmade.ecommerce.ledger.service.impl.LedgerServiceImpl;
import com.handmade.ecommerce.ledger.service.healthcheck.LedgerHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class LedgerConfiguration {
	@Bean public LedgerService _ledgerService_() {
		return new LedgerServiceImpl();
	}

	@Bean LedgerHealthChecker ledgerHealthChecker(){
    	return new LedgerHealthChecker();
    }
}
