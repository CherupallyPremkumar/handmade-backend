package com.handmade.ecommerce.common.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "attribute_values")
public class ProductAttributeValue extends BaseJpaEntity {

    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private ProductAttribute productAttribute;

    // getters and setters
}
