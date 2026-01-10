package com.handmade.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_return_request
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_return_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ReturnRequest extends AbstractJpaStateEntity {
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "customer_id", length = 36)
    private String customerId;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "return_reason", length = 50)
    private String returnReason;
    @Column(name = "customer_comments", length = 255)
    private String customerComments;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
