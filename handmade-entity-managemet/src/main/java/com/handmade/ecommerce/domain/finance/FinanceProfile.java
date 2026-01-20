package com.handmade.ecommerce.domain.finance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * FinanceProfile - Manages seller financial profile readiness and risk assessment
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_finance_profile")
public class FinanceProfile extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "verification_status", length = 50)
    private String verificationStatus; // PENDING, VERIFIED, REJECTED, SUSPENDED

    @Column(name = "risk_level", length = 50)
    private String riskLevel; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(name = "credit_limit", precision = 19, scale = 4)
    private BigDecimal creditLimit;

    @Column(name = "available_credit", precision = 19, scale = 4)
    private BigDecimal availableCredit;

    @Column(name = "outstanding_balance", precision = 19, scale = 4)
    private BigDecimal outstandingBalance;

    @Column(name = "payment_terms_days")
    private Integer paymentTermsDays = 30;

    @Column(name = "tax_status", length = 50)
    private String taxStatus;

    @Column(name = "compliance_status", length = 50)
    private String complianceStatus;

    @Column(name = "last_review_date")
    private Date lastReviewDate;

    @Column(name = "next_review_date")
    private Date nextReviewDate;
}
