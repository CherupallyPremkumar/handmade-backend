package com.handmade.ecommerce.dispute.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Dispute - Manages disputes between buyers and sellers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_dispute")
public class Dispute extends AbstractJpaStateEntity {

    @Column(name = "dispute_number", length = 100, nullable = false, unique = true)
    private String disputeNumber;

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "payment_transaction_id", length = 36)
    private String paymentTransactionId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "dispute_type", length = 100, nullable = false)
    private String disputeType; // ITEM_NOT_RECEIVED, NOT_AS_DESCRIBED

    @Column(name = "dispute_reason", length = 500, nullable = false)
    private String disputeReason;

    @Column(name = "dispute_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal disputeAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "provider_dispute_id", length = 255)
    private String providerDisputeId;

    @Column(name = "opened_at", nullable = false)
    private Date openedAt;

    @Column(name = "respond_by_date")
    private Date respondByDate;

    @Column(name = "resolved_at")
    private Date resolvedAt;

    @Column(name = "resolution_type", length = 100)
    private String resolutionType; // REFUNDED, DENIED

    @Column(name = "resolved_amount", precision = 19, scale = 4)
    private BigDecimal resolvedAmount;

    @Column(name = "winner", length = 50)
    private String winner; // BUYER, SELLER
}
