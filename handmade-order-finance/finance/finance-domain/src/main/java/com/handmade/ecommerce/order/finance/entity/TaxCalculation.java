package com.handmade.ecommerce.order.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_tax_calculation
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_tax_calculation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TaxCalculation extends BaseJpaEntity {
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "order_line_id", nullable = false, length = 36)
    private String orderLineId;
    @Column(name = "tax_code", nullable = false, length = 50)
    private String taxCode;
    @Column(name = "taxable_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxableAmount;
    @Column(name = "tax_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;
    @Column(name = "tax_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal taxRate;
    @Column(name = "jurisdiction", length = 100)
    private String jurisdiction;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
