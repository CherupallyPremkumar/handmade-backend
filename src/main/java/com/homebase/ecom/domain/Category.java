package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

public class Category extends AbstractExtendedStateEntity {

    private String name;
    private String slug;
    private String description;
    private Integer productCount = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}
