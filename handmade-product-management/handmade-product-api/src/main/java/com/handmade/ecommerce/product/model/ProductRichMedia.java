package com.handmade.ecommerce.product.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ProductRichMedia - Rich media assets for products (360 views, AR/VR)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_rich_media")
public class ProductRichMedia extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "media_type", length = 50)
    private String mediaType; // THREE_SIXTY, AR_MODEL, VR_EXPERIENCE

    @Column(name = "media_url", length = 1000, nullable = false)
    private String mediaUrl;

    @Column(name = "provider", length = 50)
    private String provider;

    @Lob
    @Column(name = "config_json")
    private String configJson;
}
