package com.handmade.ecommerce.catalog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_browse_node
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_browse_node")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class BrowseNode extends BaseJpaEntity {
    
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "slug", nullable = false, length = 255, unique = true)
    private String slug;
    @Column(name = "description")
    private String description;
    @Column(name = "parent_id", length = 36)
    private String parentId;
    @Column(name = "level")
    private String level;
    @Column(name = "display_order")
    private String displayOrder;
    @Column(name = "image_url", length = 2048)
    private String imageUrl;
    @Column(name = "icon", length = 255)
    private String icon;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "featured")
    private Boolean featured;
    @Column(name = "product_count")
    private Long productCount;
    @Column(name = "meta_title", length = 255)
    private String metaTitle;
    @Column(name = "meta_description", length = 500)
    private String metaDescription;
    @Column(name = "meta_keywords", length = 1000)
    private String metaKeywords;
    @Column(name = "banner_image_url", length = 2048)
    private String bannerImageUrl;
    @Column(name = "show_in_menu")
    private Boolean showInMenu;
    @Column(name = "show_in_footer")
    private Boolean showInFooter;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
