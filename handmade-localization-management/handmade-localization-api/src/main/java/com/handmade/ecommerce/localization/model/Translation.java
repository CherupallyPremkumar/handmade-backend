package com.handmade.ecommerce.localization.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Translation - Translated content for different languages
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_translation")
public class Translation extends AbstractJpaStateEntity {

    @Column(name = "language_id", length = 36, nullable = false)
    private String languageId;

    @Column(name = "translation_key", length = 255, nullable = false)
    private String translationKey;

    @Lob
    @Column(name = "translation_value", columnDefinition = "TEXT", nullable = false)
    private String translationValue;

    @Column(name = "entity_type", length = 50)
    private String entityType; // PRODUCT, CMS_PAGE, UI

    @Column(name = "entity_id", length = 36)
    private String entityId;
}
