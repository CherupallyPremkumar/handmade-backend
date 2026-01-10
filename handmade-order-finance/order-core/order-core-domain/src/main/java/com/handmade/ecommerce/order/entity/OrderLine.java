package com.handmade.ecommerce.order.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_order_line
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_order_line")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OrderLine extends BaseJpaEntity {
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "offer_id", nullable = false, length = 36)
    private String offerId;
    @Column(name = "quantity", nullable = false)
    private String quantity;
    @Column(name = "unit_price", precision = 19, scale = 4)
    private BigDecimal unitPrice;
    @Column(name = "tax_amount", precision = 19, scale = 4)
    private BigDecimal taxAmount;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
