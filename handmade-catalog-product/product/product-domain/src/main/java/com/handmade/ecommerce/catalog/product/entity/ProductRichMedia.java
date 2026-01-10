package com.handmade.ecommerce.catalog.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_rich_media
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_rich_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductRichMedia extends BaseJpaEntity {
    
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "media_type", length = 50)
    private String mediaType;
    @Column(name = "media_url", nullable = false, length = 1000)
    private String mediaUrl;
    @Column(name = "provider", length = 50)
    private String provider;
    @Column(name = "config_json")
    private String configJson;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
