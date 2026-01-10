package com.handmade.ecommerce.order.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_refund
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_refund")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Refund extends BaseJpaEntity {
    
    @Column(name = "return_request_id", length = 36)
    private String returnRequestId;
    @Column(name = "transaction_id", length = 36)
    private String transactionId;
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;
    @Column(name = "refund_method", length = 50)
    private String refundMethod;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
