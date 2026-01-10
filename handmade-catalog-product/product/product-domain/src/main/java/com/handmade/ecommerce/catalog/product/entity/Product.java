package com.handmade.ecommerce.catalog.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_product
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractJpaStateEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "sales_description")
    private String salesDescription;
    @Column(name = "feature_description")
    private String featureDescription;
    @Column(name = "details_description")
    private String detailsDescription;
    @Column(name = "classification_id", length = 36)
    private String classificationId;
    @Column(name = "parent_id", length = 36)
    private String parentId;
    @Column(name = "variation_theme_id", length = 36)
    private String variationThemeId;
    @Column(name = "brand", length = 255)
    private String brand;
    @Column(name = "manufacturer", length = 255)
    private String manufacturer;
    @Column(name = "is_published")
    private Boolean isPublished;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "primary_image_url", length = 2048)
    private String primaryImageUrl;
    @Column(name = "primary_image_alt_text", length = 255)
    private String primaryImageAltText;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
