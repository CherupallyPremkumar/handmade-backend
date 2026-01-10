package com.handmade.ecommerce.inventory.logistics.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_carrier
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_carrier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Carrier extends BaseJpaEntity {
    
    @Column(name = "carrier_name", nullable = false, length = 100, unique = true)
    private String carrierName;
    @Column(name = "carrier_code", nullable = false, length = 50, unique = true)
    private String carrierCode;
    @Column(name = "tracking_url_template", length = 500)
    private String trackingUrlTemplate;
    @Column(name = "api_integration_type", length = 50)
    private String apiIntegrationType;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
