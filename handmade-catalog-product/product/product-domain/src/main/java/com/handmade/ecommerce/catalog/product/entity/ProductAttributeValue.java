package com.handmade.ecommerce.catalog.product.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_attribute_value
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_attribute_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductAttributeValue extends BaseJpaEntity {
    
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "attribute_id", nullable = false, length = 36)
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
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
