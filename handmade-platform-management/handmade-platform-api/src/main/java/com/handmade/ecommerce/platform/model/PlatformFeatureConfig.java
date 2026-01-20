package com.handmade.ecommerce.platform.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Platform Feature Configuration - Feature toggles and configs per platform
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_platform_feature_config", uniqueConstraints = @UniqueConstraint(name = "uq_plat_feat_cfg", columnNames = {
        "platform_id", "feature_code" }))
public class PlatformFeatureConfig extends BaseJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;

    @Column(name = "feature_code", length = 50, nullable = false)
    private String featureCode; // FBA, ADS, SUBSCRIPTIONS

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = false;

    @Lob
    @Column(name = "config_json")
    private String configJson;
}
