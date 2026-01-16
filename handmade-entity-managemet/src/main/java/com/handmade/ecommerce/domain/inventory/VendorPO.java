package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * VendorPO - Vendor purchase order managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_vendor_po")
public class VendorPO extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36)
    private String sellerId;

    @Column(name = "po_number", length = 50, nullable = false, unique = true)
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
}
