package com.handmade.ecommerce.domain.pricing;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pricing_decision_log")
public class PricingDecisionLog extends BaseJpaEntity {

    @Column(name = "transaction_id", length = 36, nullable = false)
    private String transactionId;

    @Column(name = "original_price", precision = 19, scale = 4, nullable = false)
    private java.math.BigDecimal originalPrice;

    @Column(name = "final_price", precision = 19, scale = 4, nullable = false)
    private java.math.BigDecimal finalPrice;

    @Column(name = "applied_rules_json", columnDefinition = "TEXT", nullable = false)
    private String appliedRulesJson;

    @Column(name = "decision_time")
    private java.util.Date decisionTime;
}
