package com.handmade.ecommerce.domain.seo;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_meta_tag")
public class MetaTag extends BaseJpaEntity {

    @Column(name = "page_id", length = 36)
    private String pageId;

    @Column(name = "entity_id", length = 36)
    private String entityId;

    @Column(name = "entity_type", length = 50)
    private String entityType; // PRODUCT, CATEGORY

    @Column(name = "meta_key", length = 50)
    private String metaKey; // TITLE, DESCRIPTION, KEYWORDS

    @Column(name = "meta_value", length = 1000)
    private String metaValue;
}
