package com.handmade.ecommerce.wallet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.wallet.service.WalletService;
import com.handmade.ecommerce.wallet.service.impl.WalletServiceImpl;
import com.handmade.ecommerce.wallet.service.healthcheck.WalletHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class WalletConfiguration {
	@Bean public WalletService _walletService_() {
		return new WalletServiceImpl();
	}

	@Bean WalletHealthChecker walletHealthChecker(){
    	return new WalletHealthChecker();
    }
}
