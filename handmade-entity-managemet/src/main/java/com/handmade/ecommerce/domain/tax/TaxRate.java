package com.handmade.ecommerce.domain.tax;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TaxRate - Defines specific tax rates for a jurisdiction
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_tax_rate")
public class TaxRate extends BaseJpaEntity {

    @Column(name = "jurisdiction_id", length = 36, nullable = false)
    private String jurisdictionId;

    @Column(name = "tax_type", length = 100, nullable = false)
    private String taxType; // SALES_TAX, VAT, GST

    @Column(name = "tax_rate", precision = 10, scale = 6, nullable = false)
    private BigDecimal taxRate; // Percentage e.g. 0.082500

    @Column(name = "product_category_id", length = 36)
    private String productCategoryId;

    @Column(name = "effective_from", nullable = false)
    private Date effectiveFrom;

    @Column(name = "effective_to")
    private Date effectiveTo;

    @Column(name = "is_compound")
    private Boolean isCompound = false;

    @Column(name = "calculation_order")
    private Integer calculationOrder = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
