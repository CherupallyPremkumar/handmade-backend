package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * CatalogItemAttribute - Links catalog items to their attribute values
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_catalog_item_attribute")
public class CatalogItemAttribute extends BaseJpaEntity {

    @Column(name = "catalog_item_id", length = 36, nullable = false)
    private String catalogItemId;

    @Column(name = "attribute_id", length = 36, nullable = false)
    private String attributeId;

    @Column(name = "attribute_value", columnDefinition = "TEXT")
    private String attributeValue;
}
