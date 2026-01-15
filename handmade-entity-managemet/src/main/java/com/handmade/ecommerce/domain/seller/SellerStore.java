package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * SellerStore - Seller storefront managed by Chenile STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_store")
public class SellerStore extends AbstractJpaStateEntity {

    @Column(name = "seller_account_id", length = 36, nullable = false)
    private String sellerAccountId;

    @Column(name = "store_name", length = 255, nullable = false)
    private String storeName;

    @Column(name = "store_slug", length = 255, unique = true)
    private String storeSlug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "banner_url", length = 500)
    private String bannerUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
