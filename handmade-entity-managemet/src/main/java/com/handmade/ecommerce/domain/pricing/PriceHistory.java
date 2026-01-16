package com.handmade.ecommerce.domain.pricing;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PriceHistory - Audit log of price changes for products
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_price_history")
public class PriceHistory extends BaseJpaEntity {

    @Column(name = "offer_id", length = 36, nullable = false)
    private String offerId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "price", precision = 19, scale = 4, nullable = false)
    private BigDecimal price;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "change_reason", length = 500)
    private String changeReason;

    @Column(name = "changed_by", length = 255)
    private String changedBy;

    @Column(name = "effective_from", nullable = false)
    private Date effectiveFrom;

    @Column(name = "effective_to")
    private Date effectiveTo;
}
