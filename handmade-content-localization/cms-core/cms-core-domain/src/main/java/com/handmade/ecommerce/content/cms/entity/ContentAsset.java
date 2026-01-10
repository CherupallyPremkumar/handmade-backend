package com.handmade.ecommerce.content.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_content_asset
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_content_asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ContentAsset extends BaseJpaEntity {
    
    @Column(name = "asset_url", nullable = false, length = 1000)
    private String assetUrl;
    @Column(name = "asset_type", length = 50)
    private String assetType;
    @Column(name = "alt_text", length = 255)
    private String altText;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
