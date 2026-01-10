package com.handmade.ecommerce.content.seo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_url_mapping
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_url_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UrlMapping extends BaseJpaEntity {
    
    @Column(name = "custom_url", nullable = false, length = 500, unique = true)
    private String customUrl;
    @Column(name = "target_path", nullable = false, length = 1000)
    private String targetPath;
    @Column(name = "mapping_type", length = 50)
    private String mappingType;
    @Column(name = "is_redirect")
    private Boolean isRedirect;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
