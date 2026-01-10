package com.handmade.ecommerce.customer.cart.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_cart
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "session_id", length = 255)
    private String sessionId;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "subtotal_amount", precision = 19, scale = 4)
    private BigDecimal subtotalAmount;
    @Column(name = "tax_amount", precision = 19, scale = 4)
    private BigDecimal taxAmount;
    @Column(name = "shipping_amount", precision = 19, scale = 4)
    private BigDecimal shippingAmount;
    @Column(name = "discount_amount", precision = 19, scale = 4)
    private BigDecimal discountAmount;
    @Column(name = "total_amount", precision = 19, scale = 4)
    private BigDecimal totalAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "cart_type", length = 50)
    private String cartType;
    @Column(name = "merged_from_cart_id", length = 36)
    private String mergedFromCartId;
    @Column(name = "expires_at")
    private Date expiresAt;
    @Column(name = "converted_to_order_id", length = 36)
    private String convertedToOrderId;
    @Column(name = "conversion_date")
    private Date conversionDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
