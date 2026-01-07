package com.handmade.ecommerce.commission.domain.entity;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import com.handmade.ecommerce.commission.domain.valueobject.CommissionFeeType;
import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Individual commission rule within a policy
 */
@Entity
@Table(name = "commission_rules")
public class CommissionRule extends BaseJpaEntity {



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private CommissionPolicy policy;

    @Column(name = "rule_name", length = 100, nullable = false)
    private String ruleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type")
    private CommissionFeeType feeType;

    /**
     * The percentage or fixed value
     * For REFERRAL_FEE/PROCESSING_FEE, this is a decimal (e.g., 0.10 for 10%)
     * For SUBSCRIPTION_FEE/LISTING_FEE, this could be a fixed amount in cents
     */
    @Column(name = "threshold_value", precision = 19, scale = 4)
    private BigDecimal thresholdValue;

    @Column(name = "required")
    private Boolean required = true;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public CommissionPolicy getPolicy() { return policy; }
    public void setPolicy(CommissionPolicy policy) { this.policy = policy; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public CommissionFeeType getFeeType() { return feeType; }
    public void setFeeType(CommissionFeeType feeType) { this.feeType = feeType; }

    public BigDecimal getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(BigDecimal thresholdValue) { this.thresholdValue = thresholdValue; }

    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }

    @Override
    protected String getPrefix() {
        return "COMM_RULE";
    }
}
