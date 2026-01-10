package com.handmade.ecommerce.seller.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_configuration
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_configuration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerConfiguration extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "auto_approve_orders")
    private Boolean autoApproveOrders;
    @Column(name = "allow_cod")
    private Boolean allowCod;
    @Column(name = "return_window_days")
    private String returnWindowDays;
    @Column(name = "tax_inclusive_pricing")
    private Boolean taxInclusivePricing;
    @Column(name = "default_warehouse_id", length = 50)
    private String defaultWarehouseId;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
