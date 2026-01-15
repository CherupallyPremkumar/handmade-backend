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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_vendor_po")
public class VendorPO extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "vendor_name", length = 255)
    private String vendorName;

    @Column(name = "po_number", length = 100, unique = true)
    private String poNumber;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "expected_delivery_date")
    private Date expectedDeliveryDate;
}
