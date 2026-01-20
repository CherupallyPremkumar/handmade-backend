package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * SellerStore - Seller storefront managed by Chenile STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_store")
public class SellerStore extends AbstractJpaStateEntity {

    @Column(name = "platform_id", length = 36, nullable = false)
    private String platformId;

    @Column(name = "seller_account_id", length = 36, nullable = false)
    private String sellerAccountId;

    @Column(name = "seller_code", length = 50, nullable = false, unique = true)
    private String sellerCode;

    @Column(name = "seller_name", length = 100, nullable = false)
    private String sellerName;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "url_path", length = 100, nullable = false, unique = true)
    private String urlPath;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Column(name = "locale", length = 10)
    private String locale;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "support_email", length = 100)
    private String supportEmail;

    @Column(name = "support_phone", length = 20)
    private String supportPhone;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "configuration_id", length = 36)
    private String configurationId;

    @Column(name = "business_type", length = 50)
    private String businessType;

    @Column(name = "rating")
    private Double rating;
}
