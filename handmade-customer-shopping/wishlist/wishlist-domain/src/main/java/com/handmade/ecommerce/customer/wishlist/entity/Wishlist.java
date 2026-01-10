package com.handmade.ecommerce.customer.wishlist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_wishlist
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Wishlist extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "privacy_level", length = 50)
    private String privacyLevel;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
