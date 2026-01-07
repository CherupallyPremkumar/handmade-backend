package com.handmade.ecommerce.payout.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Audit log for payout policy changes
 * Tracks all modifications for compliance and regulatory requirements
 */
@Entity
@Table(name = "payout_policy_audit_log", indexes = {
        @Index(name = "idx_audit_policy", columnList = "policy_id, changed_at"),
        @Index(name = "idx_audit_user", columnList = "changed_by, changed_at")
})
public class PayoutPolicyAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_id", length = 255, nullable = false)
    private String policyId;

    @Column(name = "policy_version", length = 50, nullable = false)
    private String policyVersion;

    /**
     * Action performed
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "action", length = 50, nullable = false)
    private AuditAction action;

    /**
     * User who made the change
     */
    @Column(name = "changed_by", length = 255, nullable = false)
    private String changedBy;

    /**
     * When the change was made
     */
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    /**
     * Field that was changed (if applicable)
     */
    @Column(name = "field_name", length = 100)
    private String fieldName;

    /**
     * Previous value
     */
    @Lob
    @Column(name = "old_value")
    private String oldValue;

    /**
     * New value
     */
    @Lob
    @Column(name = "new_value")
    private String newValue;

    /**
     * Reason for change
     */
    @Lob
    @Column(name = "reason")
    private String reason;

    /**
     * IP address of user making change
     */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    // ========== AUDIT ACTION ENUM ==========

    public enum AuditAction {
        CREATED,
        UPDATED,
        APPROVED,
        DEPRECATED,
        RULE_ADDED,
        RULE_UPDATED,
        RULE_REMOVED,
        STATE_TRANSITION
    }

    // ========== GETTERS AND SETTERS ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
