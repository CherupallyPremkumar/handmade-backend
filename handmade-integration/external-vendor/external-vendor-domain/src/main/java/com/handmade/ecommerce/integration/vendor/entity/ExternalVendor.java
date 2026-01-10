package com.handmade.ecommerce.integration.vendor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_external_vendor
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_external_vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ExternalVendor extends BaseJpaEntity {
    
    @Column(name = "vendor_name", nullable = false, length = 255)
    private String vendorName;
    @Column(name = "vendor_type", length = 50)
    private String vendorType;
    @Column(name = "integration_protocol", length = 50)
    private String integrationProtocol;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
