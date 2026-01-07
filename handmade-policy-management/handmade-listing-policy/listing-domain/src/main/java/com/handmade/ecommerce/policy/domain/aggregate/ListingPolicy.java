package com.handmade.ecommerce.policy.domain.aggregate;

import com.handmade.ecommerce.policy.domain.entity.ListingPolicyRule;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listing_policies", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "country_code", "category", "policy_version" })
})
public class ListingPolicy extends AbstractJpaStateEntity {

    @Column(name = "policy_version", length = 20, nullable = false)
    private String policyVersion;

    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "deprecated_date")
    private LocalDate deprecatedDate;

    // Listing specific fields
    @Column(name = "max_images_per_listing")
    private Integer maxImagesPerListing;

    @Column(name = "min_images_per_listing")
    private Integer minImagesPerListing;

    @Column(name = "max_description_length")
    private Integer maxDescriptionLength;

    @Column(name = "prohibited_keywords")
    private String prohibitedKeywords;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ListingPolicyRule> rules = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void approve(String approvedBy) {
        this.approvedBy = approvedBy;
        this.effectiveDate = LocalDate.now();
    }

    public void deprecate() {
        this.deprecatedDate = LocalDate.now();
    }

    // Getters and Setters (Omitted for brevity, assume generated or added via
    // Lombok)
    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String v) {
        this.policyVersion = v;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String c) {
        this.countryCode = c;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String c) {
        this.category = c;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public LocalDate getDeprecatedDate() {
        return deprecatedDate;
    }

    public List<ListingPolicyRule> getRules() {
        return rules;
    }

    public void addRule(ListingPolicyRule rule) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be added to DRAFT policies");
        }
        rule.setPolicy(this);
        this.rules.add(rule);
    }

    public void updateRule(ListingPolicyRule rule) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be updated in DRAFT policies");
        }
        this.rules.stream()
                .filter(r -> r.getRuleName().equals(rule.getRuleName()))
                .findFirst()
                .ifPresent(r -> {
                    r.setViolationType(rule.getViolationType());
                    r.setActionRequired(rule.getActionRequired());
                    r.setRequired(rule.getRequired());
                    r.setThresholdValue(rule.getThresholdValue());
                });
    }

    public void removeRule(String ruleName) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be removed from DRAFT policies");
        }
        this.rules.removeIf(r -> r.getRuleName().equals(ruleName));
    }
}
