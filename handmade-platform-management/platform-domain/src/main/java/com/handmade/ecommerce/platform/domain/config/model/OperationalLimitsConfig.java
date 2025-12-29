package com.handmade.ecommerce.platform.domain.config.model;

import java.math.BigDecimal;

public class OperationalLimitsConfig {
    
    public int maxSellersPerDay;
    public BigDecimal maxTransactionAmount;
    public int globalRateLimit;
    public int maxProductsPerSeller;
    public int maxOrdersPerMinute;
    public int maxFailedLoginAttempts;
    public int maxApiCallsPerMinute;
    public int maxConcurrentSessions;
    public int maxFileUploadSizeMB;
    public int maxBulkOperationSize;
    
    public boolean isTransactionAllowed(BigDecimal amount) {
        if (amount == null) {
            return false;
        }
        return amount.compareTo(maxTransactionAmount) <= 0;
    }
    
    public boolean isProductCountAllowed(int productCount) {
        return productCount <= maxProductsPerSeller;
    }
    
    public boolean isBulkOperationAllowed(int operationSize) {
        return operationSize <= maxBulkOperationSize;
    }
    
    public long getFileUploadSizeBytes() {
        return (long) maxFileUploadSizeMB * 1024 * 1024;
    }
}
