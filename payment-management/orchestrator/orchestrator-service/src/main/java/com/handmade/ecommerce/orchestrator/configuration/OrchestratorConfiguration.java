package com.handmade.ecommerce.orchestrator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.orchestrator.service.OrchestratorService;
import com.handmade.ecommerce.orchestrator.service.impl.OrchestratorServiceImpl;
import com.handmade.ecommerce.orchestrator.service.healthcheck.OrchestratorHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class OrchestratorConfiguration {
	@Bean public OrchestratorService _orchestratorService_() {
		return new OrchestratorServiceImpl();
	}

	@Bean OrchestratorHealthChecker orchestratorHealthChecker(){
    	return new OrchestratorHealthChecker();
    }

	private final String paymentOrchPath="com/handmade/ecommerce/orchestrator/payment-core.xml";

	@Autowired
	ApplicationContext applicationContext;

	@Bean
	public OrchExecutor<PaymentExchange> sellerOrchExecutor() throws Exception {
		XmlOrchConfigurator<PaymentExchange> xmlOrchConfigurator = new XmlOrchConfigurator<PaymentExchange>();
		xmlOrchConfigurator.setBeanFactoryAdapter(new org.chenile.owiz.BeanFactoryAdapter() {
			@Override
			public Object lookup(String componentName) {
				return applicationContext.getBean(componentName);
			}
		});
		xmlOrchConfigurator.setFilename(paymentOrchPath);
		OrchExecutorImpl<PaymentExchange> executor = new OrchExecutorImpl<PaymentExchange>();
		executor.setOrchConfigurator(xmlOrchConfigurator);
		return executor;
	}
}
