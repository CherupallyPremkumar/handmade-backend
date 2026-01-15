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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_price_history")
public class PriceHistory extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "variant_id", length = 36)
    private String variantId;

    @Column(name = "old_price", precision = 19, scale = 4)
    private BigDecimal oldPrice;

    @Column(name = "new_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal newPrice;

    @Column(name = "change_reason", length = 255)
    private String changeReason;

    @Column(name = "change_source", length = 50)
    private String changeSource; // MANUAL, PROMOTION, BULK_UPDATE

    @Column(name = "effective_from")
    private Date effectiveFrom;

    @Column(name = "effective_to")
    private Date effectiveTo;
}
