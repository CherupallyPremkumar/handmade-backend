package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * SellerFinancialEvent - Financial transactions for sellers
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_financial_event")
public class SellerFinancialEvent extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "event_type", length = 50, nullable = false)
    private String eventType;

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "reference_id", length = 100)
    private String referenceId;

    @Column(name = "event_date", nullable = false)
    private Date eventDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
