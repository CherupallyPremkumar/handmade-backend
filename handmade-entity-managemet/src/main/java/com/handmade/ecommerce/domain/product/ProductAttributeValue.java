package com.handmade.ecommerce.domain.product;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ProductAttributeValue - Dynamic product attributes supporting multiple value types
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_attribute_value",
       indexes = @Index(name = "idx_pav_product_attr", columnList = "product_id, attribute_id", unique = true))
public class ProductAttributeValue extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "attribute_id", length = 36, nullable = false)
    private String attributeId;

    @Column(name = "value_text", length = 2048)
    private String valueText;

    @Column(name = "value_number", precision = 19, scale = 4)
    private BigDecimal valueNumber;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @Column(name = "value_date")
    private Date valueDate;

    @Column(name = "value_unit", length = 50)
    private String valueUnit;
}
