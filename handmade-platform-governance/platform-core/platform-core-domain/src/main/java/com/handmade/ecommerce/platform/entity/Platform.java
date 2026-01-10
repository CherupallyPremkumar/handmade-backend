package com.handmade.ecommerce.platform.entity;

import com.handmade.ecommerce.platform.model.PlatformStatus;
import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_platform
 * Upgraded to Amazon-grade with state management and descriptions.
 */
@Entity
@Table(name = "hm_platform")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Platform extends AbstractJpaStateEntity {

    @Column(name = "platform_code", nullable = false, length = 50, unique = true)
    private String platformCode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "marketplace_type", nullable = false, length = 20)
    private String marketplaceType;

    // i dont need platofrm status here abstarctjpa handles

    // TODO: Add relationships here
}
