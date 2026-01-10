package com.handmade.ecommerce.promotion.loyalty.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_loyalty_program
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_loyalty_program")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LoyaltyProgram extends BaseJpaEntity {
    
    @Column(name = "program_name", nullable = false, length = 100)
    private String programName;
    @Column(name = "tier_level")
    private String tierLevel;
    @Column(name = "benefits_json")
    private String benefitsJson;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
