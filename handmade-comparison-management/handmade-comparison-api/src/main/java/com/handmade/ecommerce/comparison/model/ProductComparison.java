package com.handmade.ecommerce.comparison.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ProductComparison - Represents a user's product comparison session
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_comparison")
public class ProductComparison extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "session_id", length = 255)
    private String sessionId; // For guest users

    @Column(name = "comparison_name", length = 255)
    private String comparisonName;

    @Column(name = "item_count")
    private Integer itemCount = 0;

    @Column(name = "category_id", length = 36)
    private String categoryId; // To ensure compatible products
}
