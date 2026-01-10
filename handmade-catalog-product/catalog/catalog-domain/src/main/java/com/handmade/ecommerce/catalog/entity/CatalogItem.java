package com.handmade.ecommerce.catalog.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_catalog_item
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_catalog_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CatalogItem extends BaseJpaEntity {
    
    @Column(name = "product_id", nullable = false, length = 36, unique = true)
    private String productId;
    @Column(name = "featured")
    private Boolean featured;
    @Column(name = "display_order")
    private String displayOrder;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "visibility_start_date")
    private Date visibilityStartDate;
    @Column(name = "visibility_end_date")
    private Date visibilityEndDate;
    @Column(name = "merchandising_tags")
    private String merchandisingTags;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
