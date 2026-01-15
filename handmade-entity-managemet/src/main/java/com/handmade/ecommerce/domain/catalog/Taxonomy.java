package com.handmade.ecommerce.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Taxonomy - Product taxonomy/classification system
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_taxonomy")
public class Taxonomy extends BaseJpaEntity {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "code", length = 100, nullable = false, unique = true)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
