package com.handmade.ecommerce.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * Order - Customer order managed by STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_order")
public class Order extends AbstractJpaStateEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "order_number", length = 50, nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "subtotal", precision = 19, scale = 4)
    private BigDecimal subtotal;

    @Column(name = "tax", precision = 19, scale = 4)
    private BigDecimal tax;

    @Column(name = "shipping_cost", precision = 19, scale = 4)
    private BigDecimal shippingCost;

    @Column(name = "discount", precision = 19, scale = 4)
    private BigDecimal discount;

    @Column(name = "total", precision = 19, scale = 4, nullable = false)
    private BigDecimal total;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "shipping_address_id", length = 36)
    private String shippingAddressId;

    @Column(name = "billing_address_id", length = 36)
    private String billingAddressId;
}
