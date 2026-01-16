package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * CatalogItemAttribute - Links catalog items to their attribute values
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_catalog_item_attribute")
public class CatalogItemAttribute extends BaseJpaEntity {

    @Column(name = "catalog_item_id", length = 36, nullable = false)
    private String catalogItemId;

    @Column(name = "attribute_id", length = 36, nullable = false)
    private String attributeId;

    @Column(name = "value_text", length = 2048)
    private String valueText;

    @Column(name = "value_number", precision = 19, scale = 4)
    private java.math.BigDecimal valueNumber;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @Column(name = "value_date")
    private java.util.Date valueDate;
}
