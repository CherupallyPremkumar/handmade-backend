package com.handmade.ecommerce.product.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Product - Core product entity managed by Chenile STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product")
public class Product extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(name = "sales_description", columnDefinition = "TEXT")
    private String salesDescription;

    @Lob
    @Column(name = "feature_description", columnDefinition = "TEXT")
    private String featureDescription;

    @Lob
    @Column(name = "details_description", columnDefinition = "TEXT")
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
    private Boolean isPublished = false;

    @Column(name = "primary_image_url", length = 2048)
    private String primaryImageUrl;

    @Column(name = "primary_image_alt_text", length = 255)
    private String primaryImageAltText;
}
