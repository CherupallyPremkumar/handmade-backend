package com.handmade.ecommerce.policy.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.util.Map;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;

/**
 * Individual verification step within an onboarding policy
 * 
 * Defines:
 * - What step is required (identity, tax, bank, phone)
 * - Which provider to use (Stripe, Twilio, Manual)
 * - Retry policy and SLA
 */
@Entity
@Table(name = "onboarding_policy_rules",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"policy_id", "step_name"})
       },
       indexes = {
           @Index(name = "idx_policy_steps", columnList = "policy_id, step_order")
       })
public class OnboardingPolicyRule extends BaseJpaEntity {
    
    @Id
    @Column(name = "id", length = 255)
    private String id;
    
    /**
     * Reference to parent policy
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", insertable = false, updatable = false)
    private OnboardingPolicy policy;

    @Column(name = "policy_id", length = 255, nullable = false)
    private String policyId;
    
    /**
     * Step name (matches state machine transition names)
     * Examples: "identity_verification", "tax_interview", "bank_verification", "phone_verification"
     */
    @Column(name = "step_name", length = 100, nullable = false)
    private String stepName;
    
    /**
     * Execution order (1, 2, 3...)
     * Determines sequence in onboarding flow
     */
    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;
    
    /**
     * Is this step required?
     * If false, step can be skipped
     */
    @Column(name = "required", nullable = false)
    private Boolean required = true;
    
    // ========== PROVIDER CONFIGURATION ==========
    
    /**
     * Provider type
     * Examples: "stripe_identity", "stripe_tax", "stripe_connect", "twilio", "manual"
     */
    @Column(name = "provider_type", length = 50)
    private String providerType;
    
    /**
     * Provider-specific configuration (JSON)
     * Examples:
     * - Stripe Identity: {"verification_type": "document"}
     * - Stripe Tax: {"form_type": "W9"}
     * - Twilio: {"method": "sms"}
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "provider_config", columnDefinition = "jsonb")
    private Map<String, Object> providerConfig;
    
    // ========== RETRY POLICY ==========
    
    /**
     * Maximum number of retry attempts
     * Default: 3
     */
    @Column(name = "max_retries")
    private Integer maxRetries = 3;
    
    /**
     * Delay between retries (in hours)
     * Default: 24 hours
     */
    @Column(name = "retry_delay_hours")
    private Integer retryDelayHours = 24;
    
    // ========== SLA ==========
    
    /**
     * Maximum duration allowed in this step (in days)
     * If seller exceeds this, escalation may occur
     * NULL = no time limit
     */
    @Column(name = "max_duration_days")
    private Integer maxDurationDays;
    
    // ========== GETTERS AND SETTERS ==========
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPolicyId() {
        return policyId;
    }
    
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }
    
    public String getStepName() {
        return stepName;
    }
    
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
    
    public Integer getStepOrder() {
        return stepOrder;
    }
    
    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }
    
    public Boolean getRequired() {
        return required;
    }
    
    public void setRequired(Boolean required) {
        this.required = required;
    }
    
    public Boolean isRequired() {
        return required != null && required;
    }
    
    public String getProviderType() {
        return providerType;
    }
    
    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }
    
    public Map<String, Object> getProviderConfig() {
        return providerConfig;
    }
    
    public void setProviderConfig(Map<String, Object> providerConfig) {
        this.providerConfig = providerConfig;
    }
    
    public Integer getMaxRetries() {
        return maxRetries;
    }
    
    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }
    
    public Integer getRetryDelayHours() {
        return retryDelayHours;
    }
    
    public void setRetryDelayHours(Integer retryDelayHours) {
        this.retryDelayHours = retryDelayHours;
    }
    
    public Integer getMaxDurationDays() {
        return maxDurationDays;
    }
    
    public void setMaxDurationDays(Integer maxDurationDays) {
        this.maxDurationDays = maxDurationDays;
    }
}
