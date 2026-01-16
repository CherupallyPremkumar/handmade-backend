package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * VendorPOLine - Line items in vendor purchase order (STM-managed for
 * fulfillment tracking)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_vendor_po_line")
public class VendorPOLine extends AbstractJpaStateEntity {

    @Column(name = "po_id", length = 36, nullable = false)
    private String poId;

    @Column(name = "vendor_sku", length = 100)
    private String vendorSku;

    @Column(name = "asin", length = 20)
    private String asin;

    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "quantity_confirmed")
    private Integer quantityConfirmed;

    @Column(name = "quantity_received")
    private Integer quantityReceived = 0;

    @Column(name = "unit_cost", precision = 19, scale = 4)
    private BigDecimal unitCost;
}
