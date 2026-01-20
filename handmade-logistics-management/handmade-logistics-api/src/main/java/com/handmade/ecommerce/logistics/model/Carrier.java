package com.handmade.ecommerce.logistics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Carrier - Logistics provider configuration
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_carrier")
public class Carrier extends BaseJpaEntity {

    @Column(name = "carrier_name", length = 100, nullable = false, unique = true)
    private String carrierName;

    @Column(name = "carrier_code", length = 50, nullable = false, unique = true)
    private String carrierCode; // FEDEX, UPS

    @Column(name = "tracking_url_template", length = 500)
    private String trackingUrlTemplate;

    @Column(name = "api_integration_type", length = 50)
    private String apiIntegrationType; // REST, SOAP

    @Column(name = "is_active")
    private Boolean isActive = true;
}
