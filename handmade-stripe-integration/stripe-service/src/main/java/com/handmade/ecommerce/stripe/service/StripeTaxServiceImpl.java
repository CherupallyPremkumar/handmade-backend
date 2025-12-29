package com.handmade.ecommerce.stripe.service;

import com.handmade.ecommerce.stripe.api.StripeTaxRegistrationResult;
import com.handmade.ecommerce.stripe.api.StripeTaxService;
import com.stripe.exception.StripeException;
import com.stripe.model.tax.Registration;
import com.stripe.param.tax.RegistrationCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Stripe Tax Service Implementation
 * Handles W-9/W-8 tax form collection via Stripe Tax API
 */
@Service
public class StripeTaxServiceImpl implements StripeTaxService {

    private static final Logger logger = LoggerFactory.getLogger(StripeTaxServiceImpl.class);

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private com.stripe.StripeClient getClient() {
        return new com.stripe.StripeClient(stripeApiKey);
    }

    @Override
    public String createTaxRegistrationSession(String entityId, String entityType, String returnUrl) {
        try {
            logger.info("Creating Stripe tax registration for {} ID: {}", entityType, entityId);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("entity_id", entityId);
            metadata.put("entity_type", entityType);
            metadata.put("platform", "handmade");

            RegistrationCreateParams params = RegistrationCreateParams.builder()
                    .setCountry("US")
                    .setCountryOptions(
                            RegistrationCreateParams.CountryOptions.builder()
                                    .setUs(
                                            RegistrationCreateParams.CountryOptions.Us.builder()
                                                    .setType(
                                                            RegistrationCreateParams.CountryOptions.Us.Type.STATE_SALES_TAX)
                                                    .build())
                                    .build())
                    .putExtraParam("metadata", metadata)
                    .build();

            Registration registration = getClient().tax().registrations().create(params);

            logger.info("Created Stripe tax registration: {} for {} ID: {}",
                    registration.getId(), entityType, entityId);

            return "https://tax.stripe.com/registration/" + registration.getId();

        } catch (StripeException e) {
            logger.error("Failed to create Stripe tax registration for {} ID: {}",
                    entityType, entityId, e);
            throw new RuntimeException("Failed to create tax registration", e);
        }
    }

    @Override
    public StripeTaxRegistrationResult getTaxRegistrationStatus(String registrationId) {
        try {
            Registration registration = getClient().tax().registrations().retrieve(registrationId);

            return StripeTaxRegistrationResult.builder()
                    .registrationId(registration.getId())
                    .status(registration.getStatus())
                    .completed(registration.getStatus().equals("active"))
                    .build();

        } catch (StripeException e) {
            logger.error("Failed to retrieve tax registration: {}", registrationId, e);
            throw new RuntimeException("Failed to retrieve tax registration", e);
        }
    }

    @Override
    public boolean isTaxInterviewCompleted(String registrationId) {
        StripeTaxRegistrationResult result = getTaxRegistrationStatus(registrationId);
        return result.isCompleted();
    }
}
