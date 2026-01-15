package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * Offer - Seller's offer/listing for a product managed by Chenile STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_offer")
public class Offer extends AbstractJpaStateEntity {

    @Column(name = "platform_id", length = 36, nullable = false)
    private String platformId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "seller_sku", length = 100, nullable = false)
    private String sellerSku;

    @Column(name = "condition_type", length = 50)
    private String conditionType; // New, Used, Collectible

    @Column(name = "condition_note", length = 2000)
    private String conditionNote;

    @Column(name = "fulfillment_channel", length = 50)
    private String fulfillmentChannel; // FBA or Merchant

    @Column(name = "available_quantity")
    private Integer availableQuantity = 0;

    @Column(name = "restock_date")
    private Date restockDate;

    @Column(name = "max_order_quantity")
    private Integer maxOrderQuantity;

    @Column(name = "tax_code", length = 50)
    private String taxCode;

    @Column(name = "handling_time_days")
    private Integer handlingTimeDays;

    @Column(name = "is_active")
    private Boolean isActive = true;

}
