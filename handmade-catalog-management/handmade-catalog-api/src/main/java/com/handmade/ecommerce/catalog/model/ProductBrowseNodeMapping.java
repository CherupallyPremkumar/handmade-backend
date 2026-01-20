package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ProductBrowseNodeMapping - Maps products to browse nodes (categories)
 * Note: This is different from ProductBrowseNode in product domain
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_browse_node_mapping")
public class ProductBrowseNodeMapping extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "browse_node_id", length = 36, nullable = false)
    private String browseNodeId;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;
}
