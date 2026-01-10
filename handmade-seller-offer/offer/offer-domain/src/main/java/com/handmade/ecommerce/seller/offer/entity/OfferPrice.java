package com.handmade.ecommerce.seller.offer.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_offer_price
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_offer_price")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OfferPrice extends BaseJpaEntity {
    
    @Column(name = "offer_id", nullable = false, length = 36)
    private String offerId;
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;
    @Column(name = "price_type", length = 50)
    private String priceType;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
