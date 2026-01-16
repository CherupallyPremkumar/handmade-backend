package com.handmade.ecommerce.localization.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Platform Region Policy - Links platforms to regions with policies
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_platform_region_policy", indexes = {
    @Index(name = "idx_plat_region_pol", columnList = "platform_id, region_code")
})
public class PlatformRegionPolicy extends BaseJpaEntity {

    @Column(name = "platform_id", length = 36, nullable = false)
    private String platformId;

    @Column(name = "region_code", length = 10, nullable = false)
    private String regionCode;

    @Column(name = "policy_id", length = 36, nullable = false)
    private String policyId;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
