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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_financial_event")
public class SellerFinancialEvent extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "group_id", length = 36)
    private String groupId;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "posted_date")
    private Date postedDate;

    @Column(name = "settlement_id", length = 36)
    private String settlementId;

    @Column(name = "order_id", length = 36)
    private String orderId;
}
