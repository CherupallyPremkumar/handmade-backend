package com.handmade.ecommerce.platform.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity for CommissionPolicy.
 * Infrastructure layer - persistence model.
 * Maps to platform_commission_policy table.
 */
@Entity
@Table(name = "platform_commission_policy")
public class CommissionPolicyJpaEntity extends BaseJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "platform_id")
    private String platformId;

    @Column(name = "policy_version")
    private Long policyVersion;

    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;

    @Column(name = "global_take_rate", precision = 5, scale = 2)
    private BigDecimal globalTakeRate;

    @Column(name = "flat_transaction_fee", precision = 10, scale = 2)
    private BigDecimal flatTransactionFee;

    @Column(name = "flat_fee_currency", length = 3)
    private String flatFeeCurrency;

    @Column(name = "change_reason", length = 1000)
    private String changeReason;

    @Column(name = "trace_id")
    private String traceId;

    protected CommissionPolicyJpaEntity() {}

    // Getters and Setters
    public String getPlatformId() { return platformId; }
    public void setPlatformId(String platformId) { this.platformId = platformId; }

    public Long getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(Long policyVersion) { this.policyVersion = policyVersion; }

    public LocalDateTime getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDateTime effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDateTime getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDateTime effectiveTo) { this.effectiveTo = effectiveTo; }

    public BigDecimal getGlobalTakeRate() { return globalTakeRate; }
    public void setGlobalTakeRate(BigDecimal globalTakeRate) { this.globalTakeRate = globalTakeRate; }

    public BigDecimal getFlatTransactionFee() { return flatTransactionFee; }
    public void setFlatTransactionFee(BigDecimal flatTransactionFee) { this.flatTransactionFee = flatTransactionFee; }

    public String getFlatFeeCurrency() { return flatFeeCurrency; }
    public void setFlatFeeCurrency(String flatFeeCurrency) { this.flatFeeCurrency = flatFeeCurrency; }

    public String getChangeReason() { return changeReason; }
    public void setChangeReason(String changeReason) { this.changeReason = changeReason; }

    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
}
