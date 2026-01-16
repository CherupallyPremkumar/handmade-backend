package com.handmade.ecommerce.integration.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_external_vendor")
public class ExternalVendor extends BaseJpaEntity {

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "vendor_type", length = 50)
    private String vendorType; // LOGISTICS, TAX_SERVICE, PAYMENT_GATEWAY

    @Column(name = "integration_protocol", length = 50)
    private String integrationProtocol; // REST, SOAP, WEBHOOK

    @Column(name = "is_active")
    private Boolean isActive = true;
}
