package com.handmade.ecommerce.seller.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_seller_store
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_store")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerStore extends AbstractJpaStateEntity {
    
    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;
    @Column(name = "seller_account_id", nullable = false, length = 36)
    private String sellerAccountId;
    @Column(name = "seller_code", nullable = false, length = 50, unique = true)
    private String sellerCode;
    @Column(name = "seller_name", nullable = false, length = 100)
    private String sellerName;
    @Column(name = "display_name", length = 100)
    private String displayName;
    @Column(name = "url_path", nullable = false, length = 100, unique = true)
    private String urlPath;
    @Column(name = "logo_url", length = 255)
    private String logoUrl;
    @Column(name = "currency", nullable = false, length = 10)
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
    private String rating;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
