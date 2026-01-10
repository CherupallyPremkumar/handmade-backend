package com.handmade.ecommerce.content.seo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_meta_tag
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_meta_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MetaTag extends BaseJpaEntity {
    
    @Column(name = "page_id", length = 36)
    private String pageId;
    @Column(name = "entity_id", length = 36)
    private String entityId;
    @Column(name = "entity_type", length = 50)
    private String entityType;
    @Column(name = "meta_key", length = 50)
    private String metaKey;
    @Column(name = "meta_value", length = 1000)
    private String metaValue;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
