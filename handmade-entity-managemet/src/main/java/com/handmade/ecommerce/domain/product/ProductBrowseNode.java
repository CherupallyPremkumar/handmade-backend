package com.handmade.ecommerce.domain.product;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ProductBrowseNode - Product-to-category mapping (supports multiple categories)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_browse_node",
       indexes = @Index(name = "idx_pbn_product_node", columnList = "product_id, browse_node_id", unique = true))
public class ProductBrowseNode extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "browse_node_id", length = 36, nullable = false)
    private String browseNodeId;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;
}
