package com.handmade.ecommerce.order.tax.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_tax_jurisdiction
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_tax_jurisdiction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TaxJurisdiction extends BaseJpaEntity {
    
    @Column(name = "jurisdiction_code", nullable = false, length = 100, unique = true)
    private String jurisdictionCode;
    @Column(name = "jurisdiction_name", nullable = false, length = 255)
    private String jurisdictionName;
    @Column(name = "jurisdiction_type", nullable = false, length = 50)
    private String jurisdictionType;
    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;
    @Column(name = "state_province", length = 100)
    private String stateProvince;
    @Column(name = "county", length = 100)
    private String county;
    @Column(name = "city", length = 100)
    private String city;
    @Column(name = "postal_code", length = 20)
    private String postalCode;
    @Column(name = "parent_jurisdiction_id", length = 36)
    private String parentJurisdictionId;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
