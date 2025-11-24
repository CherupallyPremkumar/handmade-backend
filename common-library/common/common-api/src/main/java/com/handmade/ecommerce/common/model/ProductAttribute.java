package com.handmade.ecommerce.common.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductAttribute extends BaseJpaEntity {

    private String name;
    // text, number, dropdown, boolean
    private String type;

    @OneToMany(
            mappedBy = "productAttribute",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductAttributeValue> values = new ArrayList<>();

    // getters and setters
}
