package com.handmade.ecommerce.customer.wishlist.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_wishlist_item
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_wishlist_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class WishlistItem extends BaseJpaEntity {
    
    @Column(name = "wishlist_id", nullable = false, length = 36)
    private String wishlistId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "added_date")
    private Date addedDate;
    @Column(name = "priority", length = 50)
    private String priority;
    @Column(name = "quantity_desired")
    private String quantityDesired;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
