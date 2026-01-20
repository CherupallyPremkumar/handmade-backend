package com.handmade.ecommerce.localization.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Region - Represents a geographic region with localization settings
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_region")
public class Region extends BaseJpaEntity {

    @Column(name = "region_code", length = 10, nullable = false, unique = true)
    private String regionCode;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "default_currency_code", length = 3)
    private String defaultCurrencyCode;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
