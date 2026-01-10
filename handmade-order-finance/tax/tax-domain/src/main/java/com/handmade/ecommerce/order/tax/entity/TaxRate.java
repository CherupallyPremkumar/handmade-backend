package com.handmade.ecommerce.order.tax.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_tax_rate
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_tax_rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TaxRate extends BaseJpaEntity {
    
    @Column(name = "jurisdiction_id", nullable = false, length = 36)
    private String jurisdictionId;
    @Column(name = "tax_type", nullable = false, length = 100)
    private String taxType;
    @Column(name = "tax_rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal taxRate;
    @Column(name = "product_category_id", length = 36)
    private String productCategoryId;
    @Column(name = "effective_from", nullable = false)
    private Date effectiveFrom;
    @Column(name = "effective_to")
    private Date effectiveTo;
    @Column(name = "is_compound")
    private Boolean isCompound;
    @Column(name = "calculation_order")
    private String calculationOrder;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
