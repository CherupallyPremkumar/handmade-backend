package com.handmade.ecommerce.content.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_cms_asset_link
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_cms_asset_link")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CmsAssetLink extends BaseJpaEntity {
    
    @Column(name = "entry_id", nullable = false, length = 36)
    private String entryId;
    @Column(name = "asset_id", nullable = false, length = 36)
    private String assetId;
    @Column(name = "field_key", length = 100)
    private String fieldKey;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
