package com.handmade.ecommerce.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "attribute_values")
public class ProductAttributeValue extends BaseJpaEntity {

    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private ProductAttribute productAttribute;
}
