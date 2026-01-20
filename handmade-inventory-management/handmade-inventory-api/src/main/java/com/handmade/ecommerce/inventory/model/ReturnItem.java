package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ReturnItem - Customer return processing
 * Tracks items coming back from customers
 * Quality inspection determines if item is restocked or disposed
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_return_item")
public class ReturnItem extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "order_line_id", length = 36)
    private String orderLineId;

    @Column(name = "sku", length = 100, nullable = false)
    private String sku;

    @Column(name = "quantity", precision = 19, scale = 4, nullable = false)
    private BigDecimal quantity;

    @Column(name = "return_reason", length = 500)
    private String returnReason;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "fulfillment_node_id", length = 36)
    private String fulfillmentNodeId;

    @Column(name = "inspection_result", length = 50)
    private String inspectionResult; // SELLABLE, DAMAGED, DEFECTIVE

    @Column(name = "inspected_by", length = 36)
    private String inspectedBy;

    @Column(name = "requested_at")
    private Date requestedAt;

    @Column(name = "received_at")
    private Date receivedAt;

    @Column(name = "restocked_at")
    private Date restockedAt;
}
