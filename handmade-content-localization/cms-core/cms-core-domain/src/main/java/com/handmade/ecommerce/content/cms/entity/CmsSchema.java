package com.handmade.ecommerce.content.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_cms_schema
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_cms_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CmsSchema extends BaseJpaEntity {
    
    @Column(name = "schema_code", nullable = false, length = 100, unique = true)
    private String schemaCode;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "validation_json")
    private String validationJson;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
