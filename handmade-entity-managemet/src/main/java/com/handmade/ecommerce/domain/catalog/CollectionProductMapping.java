package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * CollectionProductMapping - Maps products to collections
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_collection_product_mapping")
public class CollectionProductMapping extends BaseJpaEntity {

    @Column(name = "collection_id", length = 36, nullable = false)
    private String collectionId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;
}
