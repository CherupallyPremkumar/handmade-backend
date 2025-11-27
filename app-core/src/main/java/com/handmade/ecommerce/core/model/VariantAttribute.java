package com.handmade.ecommerce.core.model;

import jakarta.persistence.Entity;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
public class VariantAttribute extends BaseJpaEntity {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
