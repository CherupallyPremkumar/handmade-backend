package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "hm_product")
public class ProductEntity extends AbstractJpaStateEntity  {

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String categoryId;

    @Column(name = "feature_description")
    private String featureDescription;

    @Column(name = "sales_description")
    private String salesDescription;

    @Column(name = "details_description")
    private String detailsDescription;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String categoryId() {
        return categoryId;
    }

    public ProductEntity setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String featureDescription() {
        return featureDescription;
    }

    public ProductEntity setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
        return this;
    }

    public String salesDescription() {
        return salesDescription;
    }

    public ProductEntity setSalesDescription(String salesDescription) {
        this.salesDescription = salesDescription;
        return this;
    }

    public String detailsDescription() {
        return detailsDescription;
    }

    public ProductEntity setDetailsDescription(String detailsDescription) {
        this.detailsDescription = detailsDescription;
        return this;
    }

    public List<String> tags() {
        return tags;
    }


}
