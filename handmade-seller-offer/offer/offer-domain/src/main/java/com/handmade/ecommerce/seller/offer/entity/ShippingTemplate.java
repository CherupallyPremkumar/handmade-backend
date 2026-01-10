package com.handmade.ecommerce.seller.offer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_shipping_template
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_shipping_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ShippingTemplate extends BaseJpaEntity {
    
    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "is_default")
    private Boolean isDefault;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
