package com.handmade.ecommerce.pricing.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pricing_rule_definition")
public class PricingRuleDefinition extends AbstractJpaStateEntity {

    @Column(name = "rule_key", length = 100, nullable = false, unique = true)
    private String ruleKey;

    @Column(name = "rule_name")
    private String ruleName;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "rule_type", length = 50, nullable = false)
    private String ruleType; // DISCOUNT, SURCHARGE, FEE, COMMISSION

    @Column(name = "owner_service", length = 100)
    private String ownerService;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;
}
