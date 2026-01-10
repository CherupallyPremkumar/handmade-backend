package com.handmade.ecommerce.content.localization.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_translation
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_translation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Translation extends BaseJpaEntity {
    
    @Column(name = "language_id", nullable = false, length = 36)
    private String languageId;
    @Column(name = "translation_key", nullable = false, length = 255)
    private String translationKey;
    @Column(name = "translation_value", nullable = false)
    private String translationValue;
    @Column(name = "entity_type", length = 50)
    private String entityType;
    @Column(name = "entity_id", length = 36)
    private String entityId;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
