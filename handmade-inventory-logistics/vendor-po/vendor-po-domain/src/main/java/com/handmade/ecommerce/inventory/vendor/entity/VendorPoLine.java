package com.handmade.ecommerce.inventory.vendor.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_vendor_po_line
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_vendor_po_line")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class VendorPoLine extends BaseJpaEntity {
    
    @Column(name = "po_id", nullable = false, length = 36)
    private String poId;
    @Column(name = "vendor_sku", length = 100)
    private String vendorSku;
    @Column(name = "asin", length = 20)
    private String asin;
    @Column(name = "quantity_ordered", nullable = false)
    private String quantityOrdered;
    @Column(name = "quantity_confirmed")
    private String quantityConfirmed;
    @Column(name = "quantity_received")
    private String quantityReceived;
    @Column(name = "unit_cost", precision = 19, scale = 4)
    private BigDecimal unitCost;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
