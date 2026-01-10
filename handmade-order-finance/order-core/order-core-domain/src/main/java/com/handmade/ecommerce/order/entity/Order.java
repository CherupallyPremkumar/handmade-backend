package com.handmade.ecommerce.order.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_order
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Order extends AbstractJpaStateEntity {
    
    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;
    @Column(name = "customer_id", length = 36)
    private String customerId;
    @Column(name = "display_id", length = 50)
    private String displayId;
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    @Column(name = "total_amount", precision = 19, scale = 4)
    private BigDecimal totalAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "is_business_order")
    private Boolean isBusinessOrder;
    @Column(name = "purchase_order_number", length = 50)
    private String purchaseOrderNumber;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
