package com.handmade.ecommerce.domain.localization;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Language - Supported languages for the platform
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_language")
public class Language extends BaseJpaEntity {

    @Column(name = "language_code", length = 10, nullable = false)
    private String languageCode; // en, fr, de

    @Column(name = "native_name", length = 100)
    private String nativeName;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
