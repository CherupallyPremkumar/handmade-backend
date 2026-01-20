package com.handmade.ecommerce.domain.cms;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_content_asset")
public class ContentAsset extends BaseJpaEntity {

    @Column(name = "asset_url", length = 1000, nullable = false)
    private String assetUrl;

    @Column(name = "asset_type", length = 50)
    private String assetType; // IMAGE, VIDEO, PDF

    @Column(name = "alt_text", length = 255)
    private String altText;

    @Column(name = "file_size")
    private Long fileSize;
}
