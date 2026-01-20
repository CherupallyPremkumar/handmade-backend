package com.handmade.ecommerce.product.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ProductAdditionalImages - Additional product images beyond the primary image
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_additional_images")
public class ProductAdditionalImages extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "image_url", length = 500, nullable = false)
    private String imageUrl;

    @Column(name = "alt_text", length = 255)
    private String altText;

    @Column(name = "display_order")
    private Integer displayOrder;
}
