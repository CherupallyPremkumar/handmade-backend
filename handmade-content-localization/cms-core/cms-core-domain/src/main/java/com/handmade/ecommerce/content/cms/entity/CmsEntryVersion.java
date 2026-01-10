package com.handmade.ecommerce.content.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_cms_entry_version
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_cms_entry_version")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CmsEntryVersion extends BaseJpaEntity {
    
    @Column(name = "entry_id", nullable = false, length = 36)
    private String entryId;
    @Column(name = "version_number", nullable = false)
    private String versionNumber;
    @Column(name = "content_json")
    private String contentJson;
    @Column(name = "commit_message", length = 500)
    private String commitMessage;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
