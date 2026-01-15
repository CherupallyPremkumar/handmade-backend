package com.handmade.ecommerce.domain.comparison;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ComparisonItem - Represents a product in a comparison
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_comparison_item")
public class ComparisonItem extends BaseJpaEntity {

    @Column(name = "comparison_id", length = 36, nullable = false)
    private String comparisonId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "display_order")
    private Integer displayOrder = 0;
}
