package com.handmade.ecommerce.order.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_fee_charge
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_fee_charge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FeeCharge extends BaseJpaEntity {
    
    @Column(name = "transaction_id", nullable = false, length = 36)
    private String transactionId;
    @Column(name = "fee_type", length = 50)
    private String feeType;
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;
    @Column(name = "description", length = 255)
    private String description;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
