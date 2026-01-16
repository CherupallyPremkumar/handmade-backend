package com.handmade.ecommerce.cms.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cms_asset_link")
public class CMSAssetLink extends BaseJpaEntity {

    @Column(name = "entry_id", length = 36, nullable = false)
    private String entryId;

    @Column(name = "asset_id", length = 36, nullable = false)
    private String assetId;

    @Column(name = "field_key", length = 100)
    private String fieldKey; // The schema field referencing this asset
}
