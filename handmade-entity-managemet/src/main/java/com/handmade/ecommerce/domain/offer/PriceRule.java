package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * PriceRule - Dynamic pricing rules for offers
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_price_rule")
public class PriceRule extends BaseJpaEntity {

    @Column(name = "offer_id", length = 36, nullable = false)
    private String offerId;

    @Column(name = "rule_type", length = 50, nullable = false)
    private String ruleType;

    @Column(name = "rule_expression", columnDefinition = "TEXT")
    private String ruleExpression;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
