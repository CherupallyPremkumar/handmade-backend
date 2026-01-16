package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * BrowseNode - Hierarchical category structure for product navigation
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_browse_node")
public class BrowseNode extends BaseJpaEntity {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "slug", length = 255, nullable = false, unique = true)
    private String slug;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "featured")
    private Boolean featured = false;

    @Column(name = "product_count")
    private Long productCount = 0L;

    // SEO Metadata
    @Column(name = "meta_title", length = 255)
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Column(name = "meta_keywords", length = 1000)
    private String metaKeywords;

    @Column(name = "banner_image_url", length = 2048)
    private String bannerImageUrl;

    @Column(name = "show_in_menu")
    private Boolean showInMenu = true;

    @Column(name = "show_in_footer")
    private Boolean showInFooter = false;
}
