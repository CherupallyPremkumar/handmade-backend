package com.handmade.ecommerce.tax.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * TaxJurisdiction - Represents a specific tax authority or region
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_tax_jurisdiction")
public class TaxJurisdiction extends BaseJpaEntity {

    @Column(name = "jurisdiction_code", length = 100, nullable = false, unique = true)
    private String jurisdictionCode;

    @Column(name = "jurisdiction_name", length = 255, nullable = false)
    private String jurisdictionName;

    @Column(name = "jurisdiction_type", length = 50, nullable = false)
    private String jurisdictionType; // COUNTRY, STATE, COUNTY, CITY, DISTRICT

    @Column(name = "country_code", length = 3, nullable = false)
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
    private Boolean isActive = true;
}
