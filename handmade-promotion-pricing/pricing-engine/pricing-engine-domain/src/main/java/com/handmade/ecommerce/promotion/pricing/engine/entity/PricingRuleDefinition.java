package com.handmade.ecommerce.promotion.pricing.engine.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_pricing_rule_definition
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_pricing_rule_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PricingRuleDefinition extends BaseJpaEntity {
    
    @Column(name = "rule_key", nullable = false, length = 100, unique = true)
    private String ruleKey;
    @Column(name = "rule_name", length = 255)
    private String ruleName;
    @Column(name = "description")
    private String description;
    @Column(name = "rule_type", nullable = false, length = 50)
    private String ruleType;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "owner_service", length = 100)
    private String ownerService;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
