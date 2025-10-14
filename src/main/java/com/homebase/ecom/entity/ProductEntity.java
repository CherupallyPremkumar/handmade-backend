package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products")
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

    @Column(nullable = false)
    private Double rating = 0.0;

    @Column(nullable = false)
    private Boolean featured = false;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }
}
