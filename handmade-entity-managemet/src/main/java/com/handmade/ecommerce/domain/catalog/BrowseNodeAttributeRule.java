package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * BrowseNodeAttributeRule - Defines which attributes are required for products in a browse node
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_browse_node_attribute_rule")
public class BrowseNodeAttributeRule extends BaseJpaEntity {

    @Column(name = "browse_node_id", length = 36, nullable = false)
    private String browseNodeId;

    @Column(name = "attribute_id", length = 36, nullable = false)
    private String attributeId;

    @Column(name = "is_required")
    private Boolean isRequired = false;

    @Column(name = "display_order")
    private Integer displayOrder;
}
