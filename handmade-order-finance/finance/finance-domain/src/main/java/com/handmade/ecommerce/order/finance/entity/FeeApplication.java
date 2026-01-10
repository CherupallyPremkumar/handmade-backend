package com.handmade.ecommerce.order.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_fee_application
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_fee_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FeeApplication extends BaseJpaEntity {
    
    @Column(name = "transaction_id", nullable = false, length = 36)
    private String transactionId;
    @Column(name = "fee_definition_id", nullable = false, length = 36)
    private String feeDefinitionId;
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
