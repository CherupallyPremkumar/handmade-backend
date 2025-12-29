package com.handmade.ecommerce.platform.service.validator;

import com.handmade.ecommerce.platform.domain.config.model.ComplianceConfig;
import com.handmade.ecommerce.platform.domain.config.reader.ComplianceConfigurator;
import com.handmade.ecommerce.platform.domain.valueobject.SellerTier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplianceValidator {
    
    @Autowired
    private ComplianceConfigurator complianceConfigurator;
    
    public ValidationResult validateSeller(SellerValidationRequest request) {
        ComplianceConfig config = complianceConfigurator.complianceConfig;
        List<String> errors = new ArrayList<>();
        
        if (config.kycRequired && !request.kycVerified) {
            errors.add("KYC verification is required");
        }
        if (config.taxIdRequired && (request.taxId == null || request.taxId.isBlank())) {
            errors.add("Tax ID is required");
        }
        if (!config.isJurisdictionAllowed(request.country)) {
            errors.add("Seller jurisdiction '" + request.country + "' is not allowed");
        }
        if (request.age != null && request.age < config.minimumAge) {
            errors.add("Seller must be at least " + config.minimumAge + " years old");
        }
        if (config.requiresBusinessLicense(request.sellerTier) && 
            (request.businessLicense == null || request.businessLicense.isBlank())) {
            errors.add("Business license is required for " + request.sellerTier + " tier");
        }
        if (errors.isEmpty()) {
            return ValidationResult.success();
        }
        return ValidationResult.failure(errors);
    }
    
    public static class SellerValidationRequest {
        public boolean kycVerified;
        public String taxId;
        public String country;
        public Integer age;
        public SellerTier sellerTier;
        public String businessLicense;
    }
    
    public static class ValidationResult {
        public boolean valid;
        public List<String> errors;
        
        public static ValidationResult success() {
            ValidationResult result = new ValidationResult();
            result.valid = true;
            result.errors = new ArrayList<>();
            return result;
        }
        
        public static ValidationResult failure(List<String> errors) {
            ValidationResult result = new ValidationResult();
            result.valid = false;
            result.errors = errors;
            return result;
        }
        
        public void throwIfInvalid() {
            if (!valid) {
                throw new ComplianceViolationException(String.join(", ", errors));
            }
        }
    }
    
    public static class ComplianceViolationException extends RuntimeException {
        public ComplianceViolationException(String message) {
            super(message);
        }
    }
}
