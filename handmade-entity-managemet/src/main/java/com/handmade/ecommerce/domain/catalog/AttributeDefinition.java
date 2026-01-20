package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * AttributeDefinition - Schema definition for product attributes
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_attribute_definition")
public class AttributeDefinition extends BaseJpaEntity {

    @Column(name = "code", length = 255, nullable = false, unique = true)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "type", length = 50, nullable = false)
    private String type; // TEXT, NUMBER, BOOLEAN, ENUM, DATE

    @Column(name = "unit_of_measure", length = 50)
    private String unitOfMeasure; // e.g. "cm", "kg", "GB"

    @Column(name = "validation_regex", length = 255)
    private String validationRegex;

    @Column(name = "is_searchable")
    private Boolean isSearchable = true;

    @Column(name = "is_filterable")
    private Boolean isFilterable = true;

    @Column(name = "is_comparable")
    private Boolean isComparable = true;
}
