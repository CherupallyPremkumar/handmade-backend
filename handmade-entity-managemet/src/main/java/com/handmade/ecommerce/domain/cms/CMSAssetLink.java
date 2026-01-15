package com.handmade.ecommerce.domain.cms;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cms_asset_link")
public class CMSAssetLink extends BaseJpaEntity {

    @Column(name = "cms_entry_id", length = 36, nullable = false)
    private String cmsEntryId;

    @Column(name = "content_asset_id", length = 36, nullable = false)
    private String contentAssetId;

    @Column(name = "field_name", length = 100)
    private String fieldName; // The schema field referencing this asset
}
