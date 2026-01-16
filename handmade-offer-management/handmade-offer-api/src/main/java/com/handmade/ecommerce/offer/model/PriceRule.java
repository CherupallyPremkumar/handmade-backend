package com.handmade.ecommerce.offer.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * PriceRule - Dynamic pricing rules for offers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_price_rule")
public class PriceRule extends AbstractJpaStateEntity {

    @Column(name = "offer_id", length = 36, nullable = false)
    private String offerId;

    @Column(name = "rule_type", length = 50, nullable = false)
    private String ruleType;

    @Lob
    @Column(name = "rule_expression")
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
