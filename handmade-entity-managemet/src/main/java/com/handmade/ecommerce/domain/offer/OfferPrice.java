package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * OfferPrice - Pricing information for an offer
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_offer_price")
public class OfferPrice extends BaseJpaEntity {

    @Column(name = "offer_id", length = 36, nullable = false, unique = true)
    private String offerId;

    @Column(name = "list_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal listPrice;

    @Column(name = "sale_price", precision = 19, scale = 4)
    private BigDecimal salePrice;

    @Column(name = "sale_start_date")
    private Date saleStartDate;

    @Column(name = "sale_end_date")
    private Date saleEndDate;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "minimum_advertised_price", precision = 19, scale = 4)
    private BigDecimal minimumAdvertisedPrice;
}
