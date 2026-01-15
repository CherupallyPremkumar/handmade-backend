package com.handmade.ecommerce.domain.product;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Product - Core product entity managed by Chenile STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product")
public class Product extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "sales_description", columnDefinition = "TEXT")
    private String salesDescription;

    @Column(name = "feature_description", columnDefinition = "TEXT")
    private String featureDescription;

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

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "primary_image_url", length = 2048)
    private String primaryImageUrl;

    @Column(name = "primary_image_alt_text", length = 255)
    private String primaryImageAltText;
}
