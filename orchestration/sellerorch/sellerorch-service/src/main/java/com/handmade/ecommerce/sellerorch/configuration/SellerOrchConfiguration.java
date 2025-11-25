package com.handmade.ecommerce.sellerorch.configuration;

import com.handmade.ecommerce.sellerorch.service.*;
import com.handmade.ecommerce.sellerorch.service.impl.SellerOrchestrationService;
import org.chenile.owiz.BeanFactoryAdapter;
import org.chenile.owiz.OrchExecutor;
import org.chenile.owiz.config.impl.XmlOrchConfigurator;
import org.chenile.owiz.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Configuration class for seller orchestration.
 */
@Configuration
public class SellerOrchConfiguration {

    @Value("${handmade.seller.flow-path}")
    private String sellerOrchPath;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SellerEntryPoint sellerEntryPoint() {
        return new SellerEntryPoint();
    }

    @Bean
    SellerorchService _sellerorchService_() {
        return new SellerOrchestrationService();
    }

    @Bean
    ExecutorService sellerExecutorService() {
        return newFixedThreadPool(10);
    }

    @Bean
    ValidateSellerService validateSellerService() {
        return new ValidateSellerService();
    }

    @Bean
    CreateSellerCommandService createSellerService() {
        return new CreateSellerCommandService();
    }

    @Bean
    VerifyKycCommandService verifyKycService() {
        return new VerifyKycCommandService();
    }

    @Bean
    SetupBankAccountCommandService setupBankAccountService() {
        return new SetupBankAccountCommandService();
    }

    @Bean
    SendWelcomeEmailCommandService sendWelcomeEmailService() {
        return new SendWelcomeEmailCommandService();
    }

    @Bean
    public FilterChain<SellerContext> sellerChain() {
        return new FilterChain<>();
    }

    @Bean
    BaseSellerOrchService baseSellerOrchService() {
        return new BaseSellerOrchService();
    }

    @Bean
    ParallelChain<SellerContext> sellerParallelChain() {
        return new ParallelChain<SellerContext>(sellerExecutorService(), 2000);
    }

    @Bean
    public OrchExecutor<SellerContext> sellerOrchExecutor() throws Exception {
        XmlOrchConfigurator<SellerContext> xmlOrchConfigurator = new XmlOrchConfigurator<SellerContext>();
        xmlOrchConfigurator.setBeanFactoryAdapter(new BeanFactoryAdapter() {
            @Override
            public Object lookup(String componentName) {
                return applicationContext.getBean(componentName);
            }
        });
        xmlOrchConfigurator.setFilename(sellerOrchPath);
        OrchExecutorImpl<SellerContext> executor = new OrchExecutorImpl<SellerContext>();
        executor.setOrchConfigurator(xmlOrchConfigurator);
        return executor;
    }
}
