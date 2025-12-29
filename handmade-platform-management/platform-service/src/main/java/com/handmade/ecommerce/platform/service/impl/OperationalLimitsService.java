package com.handmade.ecommerce.platform.service.impl;

import com.handmade.ecommerce.platform.domain.config.model.OperationalLimitsConfig;
import com.handmade.ecommerce.platform.domain.config.reader.OperationalLimitsConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OperationalLimitsService {
    
    @Autowired
    private OperationalLimitsConfigurator configurator;
    
    public boolean isTransactionAllowed(BigDecimal amount) {
        return configurator.operationalLimitsConfig.isTransactionAllowed(amount);
    }
    
    public void validateTransaction(BigDecimal amount) {
        if (!isTransactionAllowed(amount)) {
            throw new LimitExceededException(
                "Transaction amount " + amount + " exceeds maximum allowed " + 
                configurator.operationalLimitsConfig.maxTransactionAmount
            );
        }
    }
    
    public boolean isProductCountAllowed(int productCount) {
        return configurator.operationalLimitsConfig.isProductCountAllowed(productCount);
    }
    
    public void validateProductCount(int productCount) {
        if (!isProductCountAllowed(productCount)) {
            throw new LimitExceededException(
                "Product count " + productCount + " exceeds maximum allowed " + 
                configurator.operationalLimitsConfig.maxProductsPerSeller
            );
        }
    }
    
    public boolean isBulkOperationAllowed(int operationSize) {
        return configurator.operationalLimitsConfig.isBulkOperationAllowed(operationSize);
    }
    
    public void validateBulkOperation(int operationSize) {
        if (!isBulkOperationAllowed(operationSize)) {
            throw new LimitExceededException(
                "Bulk operation size " + operationSize + " exceeds maximum allowed " + 
                configurator.operationalLimitsConfig.maxBulkOperationSize
            );
        }
    }
    
    public int getMaxSellersPerDay() {
        return configurator.operationalLimitsConfig.maxSellersPerDay;
    }
    
    public int getMaxApiCallsPerMinute() {
        return configurator.operationalLimitsConfig.maxApiCallsPerMinute;
    }
    
    public long getMaxFileUploadSizeBytes() {
        return configurator.operationalLimitsConfig.getFileUploadSizeBytes();
    }
    
    public static class LimitExceededException extends RuntimeException {
        public LimitExceededException(String message) {
            super(message);
        }
    }
}
