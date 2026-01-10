package com.handmade.ecommerce.content.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_content_page
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_content_page")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ContentPage extends BaseJpaEntity {
    
    @Column(name = "slug", nullable = false, length = 255, unique = true)
    private String slug;
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Column(name = "content_type", length = 50)
    private String contentType;
    @Column(name = "raw_content")
    private String rawContent;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "meta_description", length = 500)
    private String metaDescription;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
