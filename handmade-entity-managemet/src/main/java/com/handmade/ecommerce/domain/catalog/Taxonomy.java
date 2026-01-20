package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Taxonomy - Product taxonomy/classification system
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_classification")
public class Taxonomy extends BaseJpaEntity {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "code", length = 255, nullable = false, unique = true)
    private String code;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "tax_code", length = 50)
    private String taxCode;

    @Column(name = "is_leaf")
    private Boolean isLeaf = true;
}
