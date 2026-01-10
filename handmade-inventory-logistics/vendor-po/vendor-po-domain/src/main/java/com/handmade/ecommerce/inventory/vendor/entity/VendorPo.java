package com.handmade.ecommerce.inventory.vendor.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_vendor_po
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_vendor_po")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class VendorPo extends BaseJpaEntity {
    
    @Column(name = "po_number", nullable = false, length = 50, unique = true)
    private String poNumber;
    @Column(name = "vendor_code", length = 50)
    private String vendorCode;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "delivery_window_start")
    private Date deliveryWindowStart;
    @Column(name = "delivery_window_end")
    private Date deliveryWindowEnd;
    @Column(name = "destination_node_id", length = 36)
    private String destinationNodeId;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
