package com.handmade.ecommerce.orchestrator.configuration;


import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.orchestrator.service.ProductOrchestratorService;
import com.handmade.ecommerce.orchestrator.service.healthcheck.OrchestratorHealthChecker;
import com.handmade.ecommerce.orchestrator.service.impl.ProductOrchestratorServiceImpl;
import org.chenile.owiz.OrchExecutor;
import org.chenile.owiz.config.impl.XmlOrchConfigurator;
import org.chenile.owiz.impl.OrchExecutorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class OrchestratorConfiguration {
	@Bean
	public ProductOrchestratorService _orchestratorService_() {
		return new ProductOrchestratorServiceImpl();
	}

	@Bean
	OrchestratorHealthChecker orchestratorHealthChecker(){
    	return new OrchestratorHealthChecker();
    }

	private final String paymentOrchPath="com/handmade/ecommerce/orchestrator/payment-core.xml";

	@Autowired
	ApplicationContext applicationContext;

	@Bean
	public OrchExecutor<PaymentExchange> paymentOrchExecutor() throws Exception {
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
