package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ProductRelationship - Defines relationships between products (cross-sell,
 * up-sell, etc.)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_relationship")
public class ProductRelationship extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "related_product_id", length = 36, nullable = false)
    private String relatedProductId;

    @Column(name = "relationship_type", length = 50, nullable = false)
    private String relationshipType; // CROSS_SELL, UP_SELL, ACCESSORY, SIMILAR

    @Column(name = "priority")
    private Integer priority;
}
