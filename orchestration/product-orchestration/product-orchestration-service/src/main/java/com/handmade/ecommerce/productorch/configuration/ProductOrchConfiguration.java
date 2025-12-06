package com.handmade.ecommerce.productorch.configuration;

import com.handmade.ecommerce.pricing.PricingService;
import com.handmade.ecommerce.productorch.service.*;
import com.handmade.ecommerce.productorch.service.impl.ProductOrchestrationServiceImpl;
import com.handmade.ecommerce.variant.VaraiantService;
import org.chenile.owiz.BeanFactoryAdapter;
import org.chenile.owiz.OrchExecutor;
import org.chenile.owiz.config.impl.XmlOrchConfigurator;
import org.chenile.owiz.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.handmade.ecommerce.productorch.service.healthcheck.ProductorchHealthChecker;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class ProductOrchConfiguration {

	@Value("${handmade.product.flow-path}")
	private String productOrchPath;

	@Autowired
	ApplicationContext applicationContext;

	@Bean
	public ProductEntryPoint productEntryPoint() {
		return new ProductEntryPoint();
	}

	@Bean
	ProductorchService _productorchService_() {
		return new ProductOrchestrationServiceImpl();
	}

	@Bean
	ExecutorService executorService(){
		return newFixedThreadPool(10);
	}

	@Bean
	CreateProductCommandService createProductService(){
		return new CreateProductCommandService();
	}

	@Bean public FilterChain<ProductContext> productChain(){
		return new FilterChain<>();
	}

	@Bean
	ValidateProductService validateProductService(){
		return new ValidateProductService();
	}

	@Bean
	CreateVariantCommandService createVariantService(){
		return new CreateVariantCommandService();
	}

	@Bean
	CreateInventoryCommandService createInventoryService(){
		return new CreateInventoryCommandService();
	}
	@Bean
	BaseProductOrchService baseProductOrchService(){
		return new BaseProductOrchService();
	}

	@Bean
	VaraiantService varaiantService(){
		return new VaraiantServiceImpl();
	}
	@Bean ProductorchHealthChecker productorchHealthChecker(){
    	return new ProductorchHealthChecker();
    }

	@Bean
	CreatePriceCommandService createPriceService(PricingService pricingService){
		return new CreatePriceCommandService(pricingService);
	}

	@Bean
	ValidateSellerCommandService validateSellerService(){
		return new ValidateSellerCommandService();
	}

	@Bean
	ValidateComplianceCommandService validateComplianceService(){
		return new ValidateComplianceCommandService();
	}

	@Bean
	CreateCatalogEntryCommandService createCatalogEntryService(){
		return new CreateCatalogEntryCommandService();
	}

    @Bean
    public CreateShippingProfileCommandService createShippingProfileCommandService() {
        return new CreateShippingProfileCommandService();
    }

    @Bean
    public UploadImagesCommandService uploadImagesCommandService() {
        return new UploadImagesCommandService();
    }


	@Bean
	ParallelChain<ProductContext> productParallelChain() {
		return new ParallelChain<ProductContext>(executorService(),2000);
	}


	@Bean
	public OrchExecutor<ProductContext> productOrchExecutor() throws Exception {
		XmlOrchConfigurator<ProductContext> xmlOrchConfigurator = new XmlOrchConfigurator<ProductContext>();
		xmlOrchConfigurator.setBeanFactoryAdapter(new BeanFactoryAdapter() {
			@Override
			public Object lookup(String componentName) {

				return applicationContext.getBean(componentName);
			}
		});
		xmlOrchConfigurator.setFilename(productOrchPath);
		OrchExecutorImpl<ProductContext> executor = new OrchExecutorImpl<ProductContext>();
		executor.setOrchConfigurator(xmlOrchConfigurator);
		return executor;

	}

}
