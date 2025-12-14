package com.handmade.ecommerce.sellerorch.configuration;



import com.handmade.ecommerce.payment.service.PaymentService;
import com.handmade.ecommerce.sellerorch.SellerContext;
import com.handmade.ecommerce.sellerorch.SellerorchService;
import com.handmade.ecommerce.sellerorch.service.*;
import com.handmade.ecommerce.sellerorch.service.impl.SellerOrchestrationService;
import com.handmade.ecommerce.tax.service.TaxService;
import org.chenile.owiz.BeanFactoryAdapter;
import org.chenile.owiz.OrchExecutor;
import org.chenile.owiz.config.impl.XmlOrchConfigurator;
import org.chenile.owiz.impl.*;
import org.chenile.owiz.impl.ognl.OgnlRouter;
import org.chenile.proxy.builder.ProxyBuilder;
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
    Router<SellerContext> countryValidatorRouter()
    {
        return new OgnlRouter<>();
    }
    @Bean
    Chain<SellerContext> sellerOnboradingChain(){
        return new Chain<>();
    }



    @Bean
    BankAccountValidator bankAccountValidator(){
        return new BankAccountValidator();
    }

    @Bean
    Router<SellerContext> validateBankAccountRouter(){
        return new OgnlRouter<>();
    }
    @Bean
    EinValidator einValidator()
    {
        return new EinValidator();
    }




    @Bean
    Router<SellerContext> taxIdVerifier(){
        return new OgnlRouter<>();
    }

    @Bean
    Chain<SellerContext> sellerValidationParallel(){
        return new FilterChain<>();
    }

    @Bean
    BasicFieldsValidator basicFieldsValidator(){
        return new BasicFieldsValidator();
    }

    @Bean
    EmailValidatorService emailValidator(){
        return new EmailValidatorService();
    }

    @Bean
    SendWelcomeEmailCommandService sendWelcomeEmailService() {
        return new SendWelcomeEmailCommandService();
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
