package com.handmade.ecommerce.platform.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Defines how the platform makes money from transactions.
 * Domain Entity with identity (history tracking).
 * Rows are never updated, only inserted (event sourcing pattern).
 * PURE DOMAIN MODEL (no persistence annotations).
 */
public class CommissionPolicy  extends BaseJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String platformId;
    private Long policyVersion;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private BigDecimal globalTakeRate; // Percentage (0-100)
    private BigDecimal flatTransactionFee;
    private String flatFeeCurrency; 
    private String changeReason;
    private String traceId;

    protected CommissionPolicy() {} // For framework use

    public CommissionPolicy(String platformId, BigDecimal globalTakeRate, BigDecimal flatTransactionFee, String flatFeeCurrency) {
        if (globalTakeRate.compareTo(BigDecimal.ZERO) < 0 || globalTakeRate.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Take rate must be between 0 and 100");
        }
        if (flatTransactionFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Flat transaction fee must be non-negative");
        }
        this.platformId = platformId;
        this.globalTakeRate = globalTakeRate;
        this.flatTransactionFee = flatTransactionFee;
        this.flatFeeCurrency = flatFeeCurrency;
    }

    public String getPlatformId() { return platformId; }
    public void setPlatformId(String platformId) { this.platformId = platformId; }

    public Long getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(Long policyVersion) { this.policyVersion = policyVersion; }

    public LocalDateTime getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDateTime effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDateTime getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDateTime effectiveTo) { this.effectiveTo = effectiveTo; }

    public BigDecimal getGlobalTakeRate() { return globalTakeRate; }
    public BigDecimal getFlatTransactionFee() { return flatTransactionFee; }
    public String getFlatFeeCurrency() { return flatFeeCurrency; }
    
    public String getChangeReason() { return changeReason; }
    public void setChangeReason(String changeReason) { this.changeReason = changeReason; }

    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
    
    // Identity methods (required since we removed BaseJpaEntity)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
