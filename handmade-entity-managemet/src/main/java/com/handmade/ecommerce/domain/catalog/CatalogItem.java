package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * CatalogItem - Product representation in the catalog with merchandising data
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_catalog_item",
       indexes = @Index(name = "idx_catalog_item_product_id", columnList = "product_id"))
public class CatalogItem extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false, unique = true)
    private String productId;

    @Column(name = "featured")
    private Boolean featured = false;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "visibility_start_date")
    private Date visibilityStartDate;

    @Column(name = "visibility_end_date")
    private Date visibilityEndDate;

    @Column(name = "merchandising_tags", columnDefinition = "TEXT")
    private String merchandisingTags; // JSON or CSV tags
}
