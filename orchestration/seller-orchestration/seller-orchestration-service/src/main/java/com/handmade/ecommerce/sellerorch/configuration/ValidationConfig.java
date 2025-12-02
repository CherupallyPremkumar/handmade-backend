package com.handmade.ecommerce.sellerorch.configuration;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.validation.ValidationOrchestrator;
import com.handmade.ecommerce.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfig {
    @Bean
    public ValidationOrchestrator<CreateSellerRequest> sellerValidationOrchestrator(
            List<Validator<CreateSellerRequest>> validators) {
        return new ValidationOrchestrator<>(validators);
    }

    @Bean
    public ValidationOrchestrator<BankAccountDetails> bankAccountValidationOrchestrator(
            List<Validator<BankAccountDetails>> validators) {
        return new ValidationOrchestrator<>(validators);
    }

    @Bean
    public ValidationOrchestrator<PaymentRequest> paymentValidationOrchestrator(
            List<Validator<PaymentRequest>> validators) {
        return new ValidationOrchestrator<>(validators);
    }
}

