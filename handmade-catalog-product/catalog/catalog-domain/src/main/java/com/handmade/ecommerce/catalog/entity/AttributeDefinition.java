package com.handmade.ecommerce.catalog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_attribute_definition
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_attribute_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AttributeDefinition extends BaseJpaEntity {
    
    @Column(name = "code", nullable = false, length = 255, unique = true)
    private String code;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    @Column(name = "unit_of_measure", length = 50)
    private String unitOfMeasure;
    @Column(name = "validation_regex", length = 255)
    private String validationRegex;
    @Column(name = "is_searchable")
    private Boolean isSearchable;
    @Column(name = "is_filterable")
    private Boolean isFilterable;
    @Column(name = "is_comparable")
    private Boolean isComparable;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
