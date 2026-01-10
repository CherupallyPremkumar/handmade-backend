package com.handmade.ecommerce.content.cms.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_cms_entry
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_cms_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CmsEntry extends BaseJpaEntity {
    
    @Column(name = "schema_id", nullable = false, length = 36)
    private String schemaId;
    @Column(name = "slug", nullable = false, length = 255, unique = true)
    private String slug;
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "publish_start")
    private Date publishStart;
    @Column(name = "publish_end")
    private Date publishEnd;
    @Column(name = "active_version_id", length = 36)
    private String activeVersionId;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
