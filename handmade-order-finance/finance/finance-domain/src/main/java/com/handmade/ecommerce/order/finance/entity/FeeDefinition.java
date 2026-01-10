package com.handmade.ecommerce.order.finance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_fee_definition
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_fee_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FeeDefinition extends BaseJpaEntity {
    
    @Column(name = "fee_code", nullable = false, length = 50, unique = true)
    private String feeCode;
    @Column(name = "description", nullable = false, length = 255)
    private String description;
    @Column(name = "fee_type", nullable = false, length = 50)
    private String feeType;
    @Column(name = "calculation_logic")
    private String calculationLogic;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
