package com.handmade.ecommerce.content.localization.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_region
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_region")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Region extends BaseJpaEntity {
    
    @Column(name = "region_code", nullable = false, length = 10)
    private String regionCode;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "default_currency_code", length = 3)
    private String defaultCurrencyCode;
    @Column(name = "timezone", length = 50)
    private String timezone;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
